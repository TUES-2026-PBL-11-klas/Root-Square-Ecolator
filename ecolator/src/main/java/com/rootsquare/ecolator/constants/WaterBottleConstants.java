package com.rootsquare.ecolator.constants;

import java.util.List;

public final class WaterBottleConstants {

    private WaterBottleConstants() {}

    public record Bottle(String name, float value) {}

    public static final List<Bottle> ALL = List.of(
        new Bottle("Plastic 0.5L", 0.08f),
        new Bottle("Plastic 1L", 0.15f),
        new Bottle("Plastic 1.5L", 0.25f),
        new Bottle("Glass 0.5L", 0.12f),
        new Bottle("Glass 1L", 0.20f),
        new Bottle("Reusable Bottle", 0.02f)
    );
}