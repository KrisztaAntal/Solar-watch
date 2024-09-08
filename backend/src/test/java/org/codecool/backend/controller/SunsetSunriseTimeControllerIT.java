package org.codecool.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class SunsetSunriseTimeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("Kriszta")
    public void gettingSunriseWhenGetSunriseThanSendSunriseDto() throws Exception {

        mockMvc.perform(get("/api/sunrise")
                        .param("date", "2024-08-18")
                        .param("city", "Budapest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$[0].time").value("2:30:54 AM"))
                .andReturn();
    }

    @Test
    @WithMockUser("Kriszta")
    public void gettingSunsetWhenGetSunsetThanSendSunsetDto() throws Exception {

        mockMvc.perform(get("/api/sunset")
                        .param("date", "2024-08-18")
                        .param("city", "Budapest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$[0].time").value("3:16:40 PM"))
                .andReturn();
    }
}
