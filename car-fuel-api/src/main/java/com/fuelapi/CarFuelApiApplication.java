package com.fuelapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarFuelApiApplication {
    public static void main(String[] args) {
        // * Bootstraps Spring context and starts the embedded web server.
        SpringApplication.run(CarFuelApiApplication.class, args);
    }
}
