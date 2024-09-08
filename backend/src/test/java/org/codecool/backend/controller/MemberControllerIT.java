package org.codecool.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codecool.backend.model.entity.Member;
import org.codecool.backend.model.entity.Role;
import org.codecool.backend.model.payload.CreateMemberRequest;
import org.codecool.backend.repository.MemberRepository;
import org.codecool.backend.repository.RoleRepository;
import org.codecool.backend.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class MemberControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    private String jwtToken;

    @BeforeEach
    public void setup() {
        memberRepository.deleteAll();
        Member member = new Member("Lee", "lee@gmail.com", "$2a$10$OpnicZ8bxvn76ubI5Zhmz.F/pWU/fnie6Vo3GV93cT1K7mypoVq/.");
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow();
        member.getRoles().add(role);
        memberRepository.save(member);
        jwtToken = jwtUtils.generateNewJwtTokenOnNameOrPasswordChange(member.getName());
    }


    @Test
    public void canCreateNewMemberAndSaveITToDB() throws Exception {
        CreateMemberRequest mockUserRequest = new CreateMemberRequest("Eric", "eric@gmail.com", "456");

        mockMvc.perform(post("/api/register")
                        .content(jacksonObjectMapper.writeValueAsString(mockUserRequest))
                        .contentType("application/json;charset=UTF-8")
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void canChangeUsername() throws Exception {
        String updateUserNameJson = "{\"newUsername\":\"Will\"}";

        mockMvc.perform(patch("/api/user/change-username")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(updateUserNameJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Will"));

    }

    @Test
    public void canChangePassword() throws Exception {

        String updatePasswordJson = "{\"password\":\"321\"}";

        mockMvc.perform(patch("/api/user/change-password")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(updatePasswordJson))
                .andExpect(status().isOk());
    }

    @Test
    public void canChangeEmail() throws Exception {
        String updateEmailJson = "{\"newEmail\":\"JetLee@gmail.com\"}";

        mockMvc.perform(patch("/api/user/change-email")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(updateEmailJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("JetLee@gmail.com"));
    }

    @Test
    public void canDeleteUsername() throws Exception {
        mockMvc.perform(delete("/api/user/delete-me")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }
}
