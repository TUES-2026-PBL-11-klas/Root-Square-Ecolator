package com.rootsquare.ecolator.service;

import com.rootsquare.ecolator.dto.EmissionRequest;
import com.rootsquare.ecolator.dto.EmissionResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmissionService {

    public EmissionResponse calculate(EmissionRequest request) {

        // Example factors
        double cityTransportFactor = 0.18;
        double outsideCityTransportFactor = 0.25;
        double electricityFactor = 0.50;

        double cityCo2 = request.getCityTransportKm() * cityTransportFactor;
        double outsideCityCo2 = request.getOutsideCityTransportKm() * outsideCityTransportFactor;
        double electricityCo2 = request.getElectricityKwh() * electricityFactor;

        double dietCo2 = calculateDietImpact(request.getDiet());

        double totalCo2 = cityCo2 + outsideCityCo2 + electricityCo2 + dietCo2;
        double eco = totalCo2 / 1000.0;

        List<String> recommendations = new ArrayList<>();

        if (request.getCityTransportKm() > 100) {
            recommendations.add("Consider using public transport, biking, or walking more often in the city.");
        }

        if (request.getOutsideCityTransportKm() > 150) {
            recommendations.add("Try reducing long-distance car travel or using shared transport for trips outside the city.");
        }

        if ("omnivore".equalsIgnoreCase(request.getDiet())) {
            recommendations.add("Reducing meat consumption can significantly lower your environmental impact.");
        }

        if (request.getElectricityKwh() > 200) {
            recommendations.add("Try using energy-efficient appliances and reducing household electricity usage.");
        }

        if (recommendations.isEmpty()) {
            recommendations.add("Great job — your lifestyle currently has a relatively low environmental impact.");
        }

        int percentile = (int) Math.round((totalCo2 / 300.0) * 100);
        if (percentile < 1) percentile = 1;
        if (percentile > 99) percentile = 99;

        return new EmissionResponse(totalCo2, eco, percentile, recommendations);
    }

    private double calculateDietImpact(String diet) {
        if (diet == null) {
            return 0;
        }

        return switch (diet.toLowerCase()) {
            case "omnivore" -> 80.0;
            case "vegetarian" -> 45.0;
            case "vegan" -> 25.0;
            default -> 50.0;
        };
    }
}