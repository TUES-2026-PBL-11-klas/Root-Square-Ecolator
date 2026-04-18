package com.fuelapi.model;

// ─── Inbound ───────────────────────────────────────────────────────────────

public class FuelRequest {
    /** e.g. "Toyota Corolla 2020 1.8L" */
    private String carModel;

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
