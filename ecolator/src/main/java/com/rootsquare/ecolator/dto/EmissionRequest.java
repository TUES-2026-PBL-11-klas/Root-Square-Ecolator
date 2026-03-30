package com.rootsquare.ecolator.dto;

public class EmissionRequest {

    private double cityTransportKm;
    private double outsideCityTransportKm;
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