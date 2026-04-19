package com.fuelapi.model;

// * Inbound DTO accepted by /api/fuel endpoints.

public class FuelRequest {
    // * Free-text car model used to query consumption from AI provider.
    private String carModel;

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
