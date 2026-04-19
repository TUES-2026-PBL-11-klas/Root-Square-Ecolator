package com.fuelapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.assertj.core.api.Assertions.assertThat;

class CarFuelApiApplicationTest {

    @Test
    void shouldHaveSpringBootApplicationAnnotation() {
        assertThat(CarFuelApiApplication.class.isAnnotationPresent(SpringBootApplication.class)).isTrue();
    }
}
