package com.fuelapi.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FuelLookupServiceIntegrationTest {

    private MockWebServer mockWebServer;
    private FuelLookupService fuelLookupService;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        fuelLookupService = new FuelLookupService(WebClient.builder());

        WebClient mockServerClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/openai/v1/chat/completions").toString())
                .build();

        ReflectionTestUtils.setField(fuelLookupService, "webClient", mockServerClient);
        ReflectionTestUtils.setField(fuelLookupService, "apiKey", "test-api-key");
        ReflectionTestUtils.setField(fuelLookupService, "model", "test-model");
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void getFuelConsumptionShouldCallGroqApiAndParseResponse() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("{\"choices\":[{\"message\":{\"content\":\"7.3\"}}]}"));

        double value = fuelLookupService.getFuelConsumption("Toyota Corolla 2020");

        assertThat(value).isEqualTo(7.3);

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/openai/v1/chat/completions");
        assertThat(request.getHeader("Authorization")).isEqualTo("Bearer test-api-key");
        assertThat(request.getHeader("Content-Type")).isEqualTo("application/json");
        assertThat(request.getBody().readUtf8())
                .contains("\"model\":\"test-model\"")
                .contains("Toyota Corolla 2020");
    }

    @Test
    void getFuelConsumptionShouldThrowOnGroqApiError() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(401)
                .addHeader("Content-Type", "application/json")
                .setBody("{\"error\":\"unauthorized\"}"));

        assertThatThrownBy(() -> fuelLookupService.getFuelConsumption("Honda Civic 2019"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Groq API error");
    }

    @Test
    void getFuelConsumptionShouldThrowForUnparseableAiText() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("{\"choices\":[{\"message\":{\"content\":\"unknown\"}}]}"));

        assertThatThrownBy(() -> fuelLookupService.getFuelConsumption("Mystery Car"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("unparseable response");
    }
}
