package com.example.AutoEcole.dal.repository;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivateLinkRepository extends JpaRepository<PrivateLink, Long> {
    Optional<PrivateLink> findByToken(String token);
}
