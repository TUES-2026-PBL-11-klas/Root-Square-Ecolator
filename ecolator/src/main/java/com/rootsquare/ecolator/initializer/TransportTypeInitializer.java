package com.rootsquare.ecolator.initializer;

import com.rootsquare.ecolator.model.TransportType;
import com.rootsquare.ecolator.repository.TransportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class TransportTypeInitializer implements CommandLineRunner {

    private final TransportTypeRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) return;

        repository.save(TransportType.builder().name("Walking").value(0.0f).build());
        repository.save(TransportType.builder().name("Bike").value(0.01f).build());
        repository.save(TransportType.builder().name("Bus").value(0.08f).build());
        repository.save(TransportType.builder().name("Metro").value(0.05f).build());
    }
}