package com.rootsquare.ecolator.service;

import com.rootsquare.ecolator.dto.EmissionRequest;
import com.rootsquare.ecolator.dto.EmissionResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmissionService {

    public EmissionResponse calculate(EmissionRequest request) {

        double electricityFactor = 0.50;
        double fuelEmissionFactor = calculateFuelEmissionFactor(request.getFuelType());

        double cityCo2 = calculateTransportCo2(
                request.getCityTransportKm(),
                request.getCityFuelLitersPer100Km(),
                fuelEmissionFactor
        );
        double outsideCityCo2 = calculateTransportCo2(
                request.getOutsideCityTransportKm(),
                request.getOutsideCityFuelLitersPer100Km(),
                fuelEmissionFactor
        );
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

        if (request.getCityFuelLitersPer100Km() > 9 || request.getOutsideCityFuelLitersPer100Km() > 7) {
            recommendations.add("Your car fuel consumption is relatively high. Smoother driving or a more efficient vehicle could lower emissions.");
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

        System.out.println("Calculated CO2: " + totalCo2 + " kg/year, Eco: " + eco + ", Percentile: " + percentile);

        return new EmissionResponse(totalCo2, eco, percentile, recommendations);
    }

    private double calculateTransportCo2(double distanceKm, double litersPer100Km, double fuelEmissionFactor) {
        double litersUsed = (distanceKm * litersPer100Km) / 100.0;
        return litersUsed * fuelEmissionFactor;
    }

    private double calculateFuelEmissionFactor(String fuelType) {
        if (fuelType == null) {
            return 2.31;
        }

        return switch (fuelType.toLowerCase()) {
            case "diesel" -> 2.68;
            case "lpg" -> 1.51;
            case "petrol" -> 2.31;
            default -> 2.31;
        };
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
