package com.rootsquare.ecolator.initializer;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rootsquare.ecolator.constants.DietConstants;
import com.rootsquare.ecolator.constants.PollutionConstants;
import com.rootsquare.ecolator.constants.TransportConstants;
import com.rootsquare.ecolator.constants.WaterBottleConstants;
import com.rootsquare.ecolator.model.DietTypes;
import com.rootsquare.ecolator.model.PollutionConstant;
import com.rootsquare.ecolator.model.TransportType;
import com.rootsquare.ecolator.model.WaterBottleType;
import com.rootsquare.ecolator.repository.DietTypeRepository;
import com.rootsquare.ecolator.repository.PollutionConstantRepository;
import com.rootsquare.ecolator.repository.TransportTypeRepository;
import com.rootsquare.ecolator.repository.WaterBottleTypeRepository;

import lombok.RequiredArgsConstructor;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class ReferenceDataSeeder {

    private final DietTypeRepository dietRepo;
    private final TransportTypeRepository transportRepo;
    private final WaterBottleTypeRepository bottleRepo;
    private final PollutionConstantRepository pollutionRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        seedDiets();
        seedTransport();
        seedBottles();
        seedPollution();
    }

    private void seedDiets() {
        if (dietRepo.count() > 0) return;

        DietConstants.ALL.forEach(d ->
            dietRepo.save(DietTypes.builder()
                .name(d.name())
                .value(d.value())
                .build())
        );
    }

    private void seedTransport() {
        if (transportRepo.count() > 0) return;

        TransportConstants.ALL.forEach(t ->
            transportRepo.save(TransportType.builder()
                .name(t.name())
                .value(t.value())
                .build())
        );
    }

    private void seedBottles() {
        if (bottleRepo.count() > 0) return;

        WaterBottleConstants.ALL.forEach(b ->
            bottleRepo.save(WaterBottleType.builder()
                .name(b.name())
                .value(b.value())
                .build())
        );
    }

    private void seedPollution() {
        if (pollutionRepo.count() > 0) return;

        pollutionRepo.save(PollutionConstant.builder()
            .showerPerMinute(PollutionConstants.SHOWER_PER_MINUTE)
            .waterdrinkingPerLiterS(PollutionConstants.WATER_PER_LITER)
            .electricityPerKw(PollutionConstants.ELECTRICITY_PER_KW)
            .build());
    }
}