package com.rootsquare.ecolator.repository;

import com.rootsquare.ecolator.model.DietTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietTypeRepository extends JpaRepository<DietTypes, Integer> {
}