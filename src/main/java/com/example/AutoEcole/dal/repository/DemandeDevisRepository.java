package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.DemandeDevis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DemandeDevisRepository extends JpaRepository<DemandeDevis,Long> {

}
