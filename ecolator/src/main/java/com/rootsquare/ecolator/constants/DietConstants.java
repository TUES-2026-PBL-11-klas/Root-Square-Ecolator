package com.rootsquare.ecolator.constants;

import java.util.List;

public final class DietConstants {

    private DietConstants() {}

    public record Diet(String name, float value) {}

    public static final List<Diet> ALL = List.of(
        new Diet("Vegan", 2.5f),
        new Diet("Vegetarian", 3.0f),
        new Diet("Pescatarian", 3.5f),
        new Diet("Low Meat", 4.5f),
        new Diet("Medium Meat", 5.5f),
        new Diet("High Meat", 7.0f)
    );
}