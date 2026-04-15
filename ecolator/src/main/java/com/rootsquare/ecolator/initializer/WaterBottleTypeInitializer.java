package com.rootsquare.ecolator.initializer;

import com.rootsquare.ecolator.model.WaterBottleType;
import com.rootsquare.ecolator.repository.WaterBottleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(4)
public class WaterBottleTypeInitializer implements CommandLineRunner {

    private final WaterBottleTypeRepository repository;

    @Override
    public void run(String... args) {

        // Idempotent seeding (no duplicates)
        createIfNotExists("Plastic 0.5L", 0.08f);
        createIfNotExists("Plastic 1L", 0.15f);
        createIfNotExists("Plastic 1.5L", 0.25f);

        createIfNotExists("Glass 0.5L", 0.12f);
        createIfNotExists("Glass 1L", 0.20f);

        createIfNotExists("Reusable Bottle", 0.02f);
    }

    private void createIfNotExists(String name, float value) {
        repository.findByName(name)
                .orElseGet(() -> repository.save(
                        WaterBottleType.builder()
                                .name(name)
                                .value(value)
                                .build()
                ));
    }
}