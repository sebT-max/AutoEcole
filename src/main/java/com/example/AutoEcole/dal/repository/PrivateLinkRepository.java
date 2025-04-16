package com.example.AutoEcole.dal.repository;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrivateLinkRepository extends JpaRepository<PrivateLink, Long> {
    Optional<PrivateLink> findByToken(String token);
    @Query("SELECT pl FROM PrivateLink pl WHERE pl.entreprise.id = :entrepriseId")

    List<PrivateLink> findByCompanyId(Long entrepriseId);
}
