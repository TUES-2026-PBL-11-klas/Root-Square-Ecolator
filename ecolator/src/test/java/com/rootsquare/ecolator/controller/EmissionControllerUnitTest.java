package com.rootsquare.ecolator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.rootsquare.ecolator.dto.EmissionRequest;
import com.rootsquare.ecolator.dto.EmissionResponse;
import com.rootsquare.ecolator.service.EmissionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class EmissionControllerUnitTest {

    @Mock
    private EmissionService emissionService;

    @InjectMocks
    private EmissionController emissionController;

    @Test
    void calculateDelegatesToEmissionService() {
        EmissionRequest request = new EmissionRequest();
        EmissionResponse expectedResponse = new EmissionResponse(10.0, 0.01, 5, Collections.emptyList());

        when(emissionService.calculate(request)).thenReturn(expectedResponse);

        EmissionResponse actualResponse = emissionController.calculate(request);

        assertThat(actualResponse).isSameAs(expectedResponse);
    }
}
