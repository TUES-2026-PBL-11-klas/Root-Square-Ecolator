package com.rootsquare.ecolator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rootsquare.ecolator.model.UserPollution;

public interface UserPollutionRepository extends JpaRepository<UserPollution, Integer> {
}