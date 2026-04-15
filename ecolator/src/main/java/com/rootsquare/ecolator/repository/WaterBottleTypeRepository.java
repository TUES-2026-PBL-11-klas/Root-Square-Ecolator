package com.rootsquare.ecolator.repository;

import com.rootsquare.ecolator.model.WaterBottleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaterBottleTypeRepository extends JpaRepository<WaterBottleType, Integer> {
    Optional<WaterBottleType> findByName(String name);
}
