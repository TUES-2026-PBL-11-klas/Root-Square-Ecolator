package com.rootsquare.ecolator.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.rootsquare.ecolator.dto.EmissionRequest;
import com.rootsquare.ecolator.dto.EmissionResponse;
import com.rootsquare.ecolator.model.UserPollution;
import com.rootsquare.ecolator.repository.UserPollutionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmissionServiceUnitTest {

    @Mock
    private UserPollutionRepository userPollutionRepository;

    @InjectMocks
    private EmissionService emissionService;

    @Test
    void calculateWithMinimalInputsReturnsBaselineResult() {
        EmissionRequest request = new EmissionRequest();
        request.setCityTransportKm(0);
        request.setOutsideCityTransportKm(0);
        request.setCityFuelLitersPer100Km(0);
        request.setOutsideCityFuelLitersPer100Km(0);
        request.setFuelType(null);
        request.setDiet(null);
        request.setElectricityKwh(0);

        EmissionResponse response = emissionService.calculate(request);

        assertThat(response.getCo2()).isEqualTo(0.0);
        assertThat(response.getEco()).isEqualTo(0.0);
        assertThat(response.getPercentile()).isEqualTo(1);
        assertThat(response.getRecommendations()).containsExactly(
                "Great job — your lifestyle currently has a relatively low environmental impact.");

        ArgumentCaptor<UserPollution> captor = ArgumentCaptor.forClass(UserPollution.class);
        verify(userPollutionRepository).save(captor.capture());
        assertThat(captor.getValue().getTotalMonthlyCoefficient()).isEqualTo(0.0f);
        assertThat(captor.getValue().getTransportValue()).isEqualTo(0.0f);
        assertThat(captor.getValue().getWaterValue()).isEqualTo(0.0f);
    }

    @Test
    void calculateWithHighUsageAndOmnivoreDietProducesExpectedCo2AndRecommendations() {
        EmissionRequest request = new EmissionRequest();
        request.setCityTransportKm(120);
        request.setOutsideCityTransportKm(160);
        request.setCityFuelLitersPer100Km(10);
        request.setOutsideCityFuelLitersPer100Km(8);
        request.setFuelType("petrol");
        request.setDiet("omnivore");
        request.setElectricityKwh(250);

        EmissionResponse response = emissionService.calculate(request);

        assertThat(response.getCo2()).isCloseTo(262.288, org.assertj.core.api.Assertions.withPrecision(0.001));
        assertThat(response.getEco()).isCloseTo(0.262288, org.assertj.core.api.Assertions.withPrecision(0.0001));
        assertThat(response.getPercentile()).isEqualTo(87);
        assertThat(response.getRecommendations())
                .contains("Consider using public transport, biking, or walking more often in the city.")
                .contains("Try reducing long-distance car travel or using shared transport for trips outside the city.")
                .contains("Your car fuel consumption is relatively high. Smoother driving or a more efficient vehicle could lower emissions.")
                .contains("Reducing meat consumption can significantly lower your environmental impact.")
                .contains("Try using energy-efficient appliances and reducing household electricity usage.");

        ArgumentCaptor<UserPollution> captor = ArgumentCaptor.forClass(UserPollution.class);
        verify(userPollutionRepository).save(captor.capture());
        assertThat(captor.getValue().getTransportValue()).isCloseTo(57.288f, org.assertj.core.api.Assertions.withPrecision(0.001f));
        assertThat(captor.getValue().getWaterValue()).isEqualTo(125.0f);
        assertThat(captor.getValue().getTotalMonthlyCoefficient()).isCloseTo(262.288f, org.assertj.core.api.Assertions.withPrecision(0.001f));
    }

    @Test
    void calculatePercentileIsCappedAtNinetyNine() {
        EmissionRequest request = new EmissionRequest();
        request.setCityTransportKm(0);
        request.setOutsideCityTransportKm(0);
        request.setCityFuelLitersPer100Km(0);
        request.setOutsideCityFuelLitersPer100Km(0);
        request.setFuelType("diesel");
        request.setDiet("omnivore");
        request.setElectricityKwh(100000);

        EmissionResponse response = emissionService.calculate(request);

        assertThat(response.getPercentile()).isEqualTo(99);
        assertThat(response.getCo2()).isGreaterThan(1000);
    }
}
