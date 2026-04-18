package com.rootsquare.ecolator.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pollution_constants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollutionConstant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float showerPerMinute;
    private Float waterdrinkingPerLiterS;
    private Float electricityPerKw;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}