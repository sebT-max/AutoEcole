package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.CodePromo;
import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodePromoRepository extends JpaRepository<CodePromo, Long> {
    Optional<CodePromo> findByCode(String code);  // Méthode pour récupérer le code promo par code
}
