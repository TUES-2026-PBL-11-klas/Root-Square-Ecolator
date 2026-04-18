package com.rootsquare.ecolator.dto;

public class EmissionRequest {

    private double cityTransportKm;
    private double outsideCityTransportKm;
    private double cityFuelLitersPer100Km;
    private double outsideCityFuelLitersPer100Km;
    private String fuelType;
    private String diet;
    private double electricityKwh;

    public double getCityTransportKm() {
        return cityTransportKm;
    }

    public void setCityTransportKm(double cityTransportKm) {
        this.cityTransportKm = cityTransportKm;
    }

    public double getOutsideCityTransportKm() {
        return outsideCityTransportKm;
    }

    public void setOutsideCityTransportKm(double outsideCityTransportKm) {
        this.outsideCityTransportKm = outsideCityTransportKm;
    }

    public double getCityFuelLitersPer100Km() {
        return cityFuelLitersPer100Km;
    }

    public void setCityFuelLitersPer100Km(double cityFuelLitersPer100Km) {
        this.cityFuelLitersPer100Km = cityFuelLitersPer100Km;
    }

    public double getOutsideCityFuelLitersPer100Km() {
        return outsideCityFuelLitersPer100Km;
    }

    public void setOutsideCityFuelLitersPer100Km(double outsideCityFuelLitersPer100Km) {
        this.outsideCityFuelLitersPer100Km = outsideCityFuelLitersPer100Km;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public double getElectricityKwh() {
        return electricityKwh;
    }

    public void setElectricityKwh(double electricityKwh) {
        this.electricityKwh = electricityKwh;
    }
}
