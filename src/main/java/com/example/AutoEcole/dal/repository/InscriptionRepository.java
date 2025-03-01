package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    @Query("SELECT r FROM Reservation r JOIN FETCH r.passengers p JOIN FETCH r.reservationActivities ra WHERE r.id = :id")
    Optional<com.example.AutoEcole.dal.domain.entity.Inscription> findByIdWithDetails(Long id);
}
