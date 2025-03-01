package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.CodePromo;
import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodePromoRepository extends JpaRepository<CodePromo, Long> {
}
