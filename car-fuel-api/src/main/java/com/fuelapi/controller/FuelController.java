package com.fuelapi.controller;

import com.fuelapi.model.FuelRequest;
import com.fuelapi.service.FuelLookupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    private final FuelLookupService fuelLookupService;

    public FuelController(FuelLookupService fuelLookupService) {
        this.fuelLookupService = fuelLookupService;
    }

    /**
     * POST /api/fuel
     * Body: { "carModel": "Toyota Corolla 2020" }
     * Returns: 7.1   (plain number, L/100 km)
     */
    @PostMapping
    public ResponseEntity<Double> getFuelUsage(@RequestBody FuelRequest request) {
        if (request.getCarModel() == null || request.getCarModel().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        double fuelUsage = fuelLookupService.getFuelConsumption(request.getCarModel());
        return ResponseEntity.ok(fuelUsage);
    }

    /**
     * GET /api/fuel?model=Toyota+Corolla+2020
     * Alternative endpoint — handy for quick browser/curl tests.
     */
    @GetMapping
    public ResponseEntity<Double> getFuelUsageGet(@RequestParam String model) {
        if (model == null || model.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        double fuelUsage = fuelLookupService.getFuelConsumption(model);
        return ResponseEntity.ok(fuelUsage);
    }
}
