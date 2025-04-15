package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    @Query("SELECT i FROM Inscription i JOIN FETCH i.stage WHERE i.user.id = :userId")
    List<Inscription> findByUserIdWithDetails(Long userId);
}
