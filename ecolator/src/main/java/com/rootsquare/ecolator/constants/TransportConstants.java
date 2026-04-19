package com.rootsquare.ecolator.constants;

import java.util.List;

public final class TransportConstants {

    private TransportConstants() {}

    public record Transport(String name, float value) {}

    public static final List<Transport> ALL = List.of(
        new Transport("Walking", 0.0f),
        new Transport("Bike", 0.01f),
        new Transport("Bus", 0.08f),
        new Transport("Metro", 0.05f)
    );
}