package com.fuelapi.integration;

import com.fuelapi.service.FuelLookupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class FuelApiIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FuelLookupService fuelLookupService;

    @Test
    void contextShouldLoadAndHealthEndpointShouldBeUp() {
        webTestClient.get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("UP");
    }

    @Test
    void postFuelShouldReturnNumericValueForValidBody() {
        stub().reset();
        stub().response = 6.8;

        webTestClient.post()
                .uri("/api/fuel")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"carModel\":\"Toyota Corolla 2020\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class)
                .value(value -> assertThat(value).isEqualTo(6.8));

        assertThat(stub().calls).isEqualTo(1);
        assertThat(stub().lastModel).isEqualTo("Toyota Corolla 2020");
    }

    @Test
    void postFuelShouldReturnBadRequestForBlankModel() {
        stub().reset();

        webTestClient.post()
                .uri("/api/fuel")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"carModel\":\"   \"}")
                .exchange()
                .expectStatus().isBadRequest();

        assertThat(stub().calls).isZero();
    }

    @Test
    void getFuelShouldReturnNumericValueForValidQueryParam() {
        stub().reset();
        stub().response = 7.1;

        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/fuel")
                .queryParam("model", "Honda Civic 2019")
                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class)
                .value(value -> assertThat(value).isEqualTo(7.1));

        assertThat(stub().calls).isEqualTo(1);
        assertThat(stub().lastModel).isEqualTo("Honda Civic 2019");
    }

    @Test
    void getFuelShouldReturnBadRequestForBlankQueryParam() {
        stub().reset();

        webTestClient.get()
                .uri("/api/fuel?model=   ")
                .exchange()
                .expectStatus().isBadRequest();

        assertThat(stub().calls).isZero();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        FuelLookupService fuelLookupService() {
            return new StubFuelLookupService();
        }
    }

    static class StubFuelLookupService extends FuelLookupService {
        private int calls;
        private String lastModel;
        private double response = 0.0;

        StubFuelLookupService() {
            super(WebClient.builder());
        }

        @Override
        public double getFuelConsumption(String carModel) {
            calls++;
            lastModel = carModel;
            return response;
        }

        void reset() {
            calls = 0;
            lastModel = null;
            response = 0.0;
        }
    }

    private StubFuelLookupService stub() {
        return (StubFuelLookupService) fuelLookupService;
    }
}
