package com.rootsquare.ecolator.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rootsquare.ecolator.dto.EmissionRequest;
import com.rootsquare.ecolator.repository.UserPollutionRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EcolatorIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserPollutionRepository userPollutionRepository;

    @Test
    void calculateEndpointReturnsResultAndPersistsUserPollution() throws Exception {
        EmissionRequest request = new EmissionRequest();
        request.setCityTransportKm(80);
        request.setOutsideCityTransportKm(100);
        request.setCityFuelLitersPer100Km(8);
        request.setOutsideCityFuelLitersPer100Km(7);
        request.setFuelType("diesel");
        request.setDiet("vegetarian");
        request.setElectricityKwh(220);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/emissions/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.co2").isNumber())
                .andExpect(jsonPath("$.eco").isNumber())
                .andExpect(jsonPath("$.percentile").isNumber())
                .andExpect(jsonPath("$.recommendations").isArray())
                .andExpect(jsonPath("$.recommendations", hasSize(1)));

        assertThat(userPollutionRepository.findAll()).hasSize(1);
        assertThat(userPollutionRepository.findAll().get(0).getCreatedAt()).isNotNull();
    }
}
