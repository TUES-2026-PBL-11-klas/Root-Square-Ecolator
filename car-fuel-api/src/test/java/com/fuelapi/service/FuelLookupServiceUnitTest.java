package com.fuelapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FuelLookupServiceUnitTest {

    private FuelLookupService fuelLookupService;

    @BeforeEach
    void setUp() {
        fuelLookupService = new FuelLookupService(WebClient.builder());
    }

    @Test
    void buildPromptShouldContainModelAndRules() {
        String prompt = ReflectionTestUtils.invokeMethod(fuelLookupService, "buildPrompt", "Toyota Corolla 2020");

        assertThat(prompt).contains("Toyota Corolla 2020");
        assertThat(prompt).contains("Reply with ONLY the numeric value");
        assertThat(prompt).contains("If the car is electric or hydrogen, reply with 0");
    }

    @Test
    void parseNumberShouldParseCommaAndText() {
        Double value = ReflectionTestUtils.invokeMethod(
                fuelLookupService,
                "parseNumber",
                "7,4 L/100 km",
                "Toyota Corolla 2020"
        );

        assertThat(value).isEqualTo(7.41);
    }

    @Test
    void parseNumberShouldTrimExtraDotsAfterSecondDecimalPoint() {
        Double value = ReflectionTestUtils.invokeMethod(
                fuelLookupService,
                "parseNumber",
                "7.2.9",
                "Honda Civic"
        );

        assertThat(value).isEqualTo(7.2);
    }

    @Test
    void parseNumberShouldFailForUnparseableText() {
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(
                fuelLookupService,
                "parseNumber",
                "N/A",
                "Unknown Car"
        ))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("unparseable response");
    }

    @Test
    void parseNumberShouldFailForInvalidNumericValue() {
        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(
                fuelLookupService,
                "parseNumber",
                ".",
                "Unknown Car"
        ))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Could not parse fuel value");
    }
}
