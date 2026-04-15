package com.rootsquare.ecolator.initializer;

import com.rootsquare.ecolator.model.PollutionConstant;
import com.rootsquare.ecolator.repository.PollutionConstantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(3)
public class PollutionConstantInitializer implements CommandLineRunner {

    private final PollutionConstantRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) return;

        repository.save(PollutionConstant.builder()
                .showerPerMinute(0.08f)     // kg CO2 per minute
                .waterdrinkingPerLiterS(0.0003f)    // kg CO2 per liter
                .electricityPerKw(0.4f)     // kg CO2 per kWh
                .build());
    }
}