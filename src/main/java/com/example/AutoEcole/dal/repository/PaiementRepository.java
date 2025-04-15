package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Paiement;
import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
