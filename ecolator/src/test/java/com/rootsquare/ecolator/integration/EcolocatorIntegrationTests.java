package com.rootsquare.ecolator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rootsquare.ecolator.dto.EmissionRequest;
import com.rootsquare.ecolator.repository.UserPollutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class EcolocatorIntegrationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserPollutionRepository userPollutionRepository;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

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
                .andExpect(jsonPath("$.recommendations", hasSize(3)));

        assertThat(userPollutionRepository.findAll()).hasSize(1);
        assertThat(userPollutionRepository.findAll().get(0).getCreatedAt()).isNotNull();
    }
}
