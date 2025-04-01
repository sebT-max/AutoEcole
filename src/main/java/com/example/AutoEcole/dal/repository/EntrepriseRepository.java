package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    @Query( "Select e " +
            "from Entreprise e " +
            "where e.email ilike :email")
    Optional<Entreprise> findByUsername(String email);

    @Query( "Select e " +
            "from Entreprise e " +
            "where e.email ilike :email")
    Optional<Entreprise> findByEmail(String email);

    @Query(
            "SELECT COUNT(*)>0 " +
                    "FROM Entreprise  " +
                    "WHERE email ILIKE :email" )
    boolean existsByEmail(String email);

    @Query("SELECT e FROM Entreprise e JOIN FETCH e.role WHERE e.id = :id")
    Optional<Entreprise> findByIdWithRole(@Param("id") Long id);
}
