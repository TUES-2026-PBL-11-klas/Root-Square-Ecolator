package com.rootsquare.ecolator.dto;

import java.util.List;

public class EmissionResponse {

    private double co2;
    private double eco;
    private int percentile;
    private List<String> recommendations;

    public EmissionResponse(double co2, double eco, int percentile, List<String> recommendations) {
        this.co2 = co2;
        this.eco = eco;
        this.percentile = percentile;
        this.recommendations = recommendations;
    }

    public double getCo2() {
        return co2;
    }

    public double getEco() {
        return eco;
    }

    public int getPercentile() {
        return percentile;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }
}