package org.codecool.backend.controller;

import org.codecool.backend.model.entity.Member;
import org.codecool.backend.model.entity.Role;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

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
        Role role = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        member.getRoles().add(role);
        memberRepository.save(member);
        Member member2 = new Member("Will", "Will@gmail.com", "$2a$10$OpnicZ8bxvn76ubI5Zhmz.F/pWU/fnie6Vo3GV93cT1K7mypoVq/.");
        Role role2 = roleRepository.findByName("ROLE_USER").orElseThrow();
        member2.getRoles().add(role2);
        memberRepository.save(member2);
        jwtToken = jwtUtils.generateNewJwtTokenOnNameOrPasswordChange(member.getName());
    }

    @Test
    public void canAddNewCity() throws Exception {
        String createNewCityRequestJson = "{" +
                "\"name\":\"Toronto\"," +
                "\"country\":\"CA\"," +
                "\"state\":\"Ontario\"," +
                "\"lat\":43.651070," +
                "\"lon\":-79.347015" +
                "}" +
                "}";

        mockMvc.perform(post("/api/edit/add/city")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(createNewCityRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Toronto"));
    }

    @Test
    public void canAddNewSunrise() throws Exception {
        String createSunriseRequestJson = "{" +
                "\"date\":\"2024-12-11\"," +
                "\"timeOfSunrise\":\"12:00:00 AM\"," +
                "\"city\":{" +
                "\"name\":\"Chelsea\"," +
                "\"lat\":51.4875167," +
                "\"lon\":-0.1687007," +
                "\"country\":\"GB\"," +
                "\"state\":\"England\"" +
                "}" +
                "}";

        mockMvc.perform(post("/api/edit/add/sunrise")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(createSunriseRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").value("12:00:00 AM"))
                .andExpect(jsonPath("$.city.name").value("Chelsea"));
    }

    @Test
    public void canAddNewSunset() throws Exception {
        String createSunsetRequestJson = "{" +
                "\"date\":\"2024-12-11\"," +
                "\"timeOfSunset\":\"12:00:00 PM\"," +
                "\"city\":{" +
                "\"name\":\"Chelsea\"," +
                "\"lat\":51.4875167," +
                "\"lon\":-0.1687007," +
                "\"country\":\"GB\"," +
                "\"state\":\"England\"" +
                "}" +
                "}";

        mockMvc.perform(post("/api/edit/add/sunset")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(createSunsetRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").value("12:00:00 PM"))
                .andExpect(jsonPath("$.city.name").value("Chelsea"));
    }

    @Test
    public void canUpdateSunrise() throws Exception {
        String updateSunriseJson = "{\"time\":\"2:30:50 AM\"}";

        mockMvc.perform(patch("/api/edit/update/sunrise/2024-08-17+London+GB+England")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(updateSunriseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").value("2:30:50 AM"));
    }

    @Test
    public void canUpdateSunset() throws Exception {
        String updateSunriseJson = "{\"time\":\"2:30:50 PM\"}";

        mockMvc.perform(patch("/api/edit/update/sunset/2024-08-17+London+GB+England")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(updateSunriseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").value("2:30:50 PM"));
    }

    @Test
    public void canUpdateCity() throws Exception {
        String updateSunriseJson = "{\"state\":\"Hungary\"}";

        mockMvc.perform(patch("/api/edit/update/city/Budapest+HU+null")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8")
                        .content(updateSunriseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("Hungary"));
    }

    @Test
    public void canDeleteUserByID() throws Exception {
        Member memberToDelete = memberRepository.findByName("Will").orElseThrow();

        mockMvc.perform(delete("/api/edit/delete/user/" + memberToDelete.getMemberId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void canDeleteSunrise() throws Exception {
        mockMvc.perform(delete("/api/edit/delete/sunrise/2024-08-17+London+GB+England")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void canDeleteSunset() throws Exception {
        mockMvc.perform(delete("/api/edit/delete/sunset/2024-08-17+London+GB+England")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void canDeleteCity() throws Exception {
        mockMvc.perform(delete("/api/edit/delete/city/London+CA+Ontario")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }
}
