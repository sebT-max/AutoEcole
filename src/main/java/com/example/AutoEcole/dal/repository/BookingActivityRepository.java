package com.example.AutoEcole.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingActivityRepository extends JpaRepository<com.example.AutoEcole.dal.domain.entity.Document, Long> {
    List<com.example.AutoEcole.dal.domain.entity.Document> findByBookingId(Long reservationId);
    List<com.example.AutoEcole.dal.domain.entity.Document> findByPassengerId(Long passengerId);
    void deleteByBookingId(Long reservationId);
}
