package com.example.AutoEcole.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JourneyRepository extends JpaRepository<com.example.AutoEcole.dal.domain.entity.Stage, Long> {
}
