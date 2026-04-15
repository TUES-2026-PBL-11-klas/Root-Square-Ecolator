package com.rootsquare.ecolator.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_pollution")
public class UserPollution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId; // 👈 just reference

    private Float transportValue;
    private Float showerValue;
    private Float waterValue;
    private Float totalMonthlyCoefficient;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}