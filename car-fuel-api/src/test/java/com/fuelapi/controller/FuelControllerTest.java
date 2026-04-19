package com.fuelapi.controller;

import com.fuelapi.model.FuelRequest;
import com.fuelapi.service.FuelLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

class FuelControllerTest {

    private TestFuelLookupService fuelLookupService;

    private FuelController fuelController;

    @BeforeEach
    void setUp() {
        fuelLookupService = new TestFuelLookupService();
        fuelController = new FuelController(fuelLookupService);
    }

    @Test
    void postShouldReturnFuelUsageForValidModel() {
        FuelRequest request = new FuelRequest();
        request.setCarModel("Toyota Corolla 2020");
        fuelLookupService.response = 6.8;

        ResponseEntity<Double> response = fuelController.getFuelUsage(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(6.8);
        assertThat(fuelLookupService.calls).isEqualTo(1);
        assertThat(fuelLookupService.lastModel).isEqualTo("Toyota Corolla 2020");
    }

    @Test
    void postShouldReturnBadRequestForNullModel() {
        FuelRequest request = new FuelRequest();
        request.setCarModel(null);

        ResponseEntity<Double> response = fuelController.getFuelUsage(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isNull();
        assertThat(fuelLookupService.calls).isZero();
    }

    @Test
    void postShouldReturnBadRequestForBlankModel() {
        FuelRequest request = new FuelRequest();
        request.setCarModel("   ");

        ResponseEntity<Double> response = fuelController.getFuelUsage(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isNull();
        assertThat(fuelLookupService.calls).isZero();
    }

    @Test
    void getShouldReturnFuelUsageForValidModel() {
        fuelLookupService.response = 7.2;

        ResponseEntity<Double> response = fuelController.getFuelUsageGet("Honda Civic 2019");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(7.2);
        assertThat(fuelLookupService.calls).isEqualTo(1);
        assertThat(fuelLookupService.lastModel).isEqualTo("Honda Civic 2019");
    }

    @Test
    void getShouldReturnBadRequestForBlankModel() {
        ResponseEntity<Double> response = fuelController.getFuelUsageGet(" ");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isNull();
        assertThat(fuelLookupService.calls).isZero();
    }

    private static class TestFuelLookupService extends FuelLookupService {
        private int calls;
        private String lastModel;
        private double response;

        private TestFuelLookupService() {
            super(WebClient.builder());
        }

        @Override
        public double getFuelConsumption(String carModel) {
            calls++;
            lastModel = carModel;
            return response;
        }
    }
}
