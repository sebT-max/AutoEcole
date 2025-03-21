package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

}
