package com.rootsquare.ecolator.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "water_bottle_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterBottleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Float value;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}