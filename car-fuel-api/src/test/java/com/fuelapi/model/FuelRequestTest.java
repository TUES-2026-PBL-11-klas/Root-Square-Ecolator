package com.fuelapi.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FuelRequestTest {

    @Test
    void shouldSetAndGetCarModel() {
        FuelRequest request = new FuelRequest();

        request.setCarModel("Toyota Corolla 2020 1.8L");

        assertThat(request.getCarModel()).isEqualTo("Toyota Corolla 2020 1.8L");
    }
}
