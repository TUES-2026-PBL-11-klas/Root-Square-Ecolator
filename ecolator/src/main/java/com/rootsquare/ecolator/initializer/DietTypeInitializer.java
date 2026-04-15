package com.rootsquare.ecolator.initializer;

import com.rootsquare.ecolator.model.DietTypes;
import com.rootsquare.ecolator.repository.DietTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class DietTypeInitializer implements CommandLineRunner {

    private final DietTypeRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) return;

        repository.save(DietTypes.builder().name("Vegan").value(2.5f).build());
        repository.save(DietTypes.builder().name("Vegetarian").value(3.0f).build());
        repository.save(DietTypes.builder().name("Pescatarian").value(3.5f).build());
        repository.save(DietTypes.builder().name("Low Meat").value(4.5f).build());
        repository.save(DietTypes.builder().name("Medium Meat").value(5.5f).build());
        repository.save(DietTypes.builder().name("High Meat").value(7.0f).build());
    }
}