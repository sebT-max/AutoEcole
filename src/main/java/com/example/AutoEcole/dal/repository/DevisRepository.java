package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Devis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DevisRepository extends JpaRepository<Devis, Long> {
}
