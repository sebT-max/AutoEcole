package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long>, JpaSpecificationExecutor<Stage> {
    // Méthode de recherche avec un terme de recherche
    @Query("SELECT s FROM Stage s WHERE " +
            "LOWER(s.organisation) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.city) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Stage> searchByFilters(String searchTerm);
}

