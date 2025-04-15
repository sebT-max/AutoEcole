package com.example.AutoEcole.dal.repository;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PrivateLinkRepository extends JpaRepository<PrivateLink, Long> {
    Optional<PrivateLink> findByToken(String token);
}
