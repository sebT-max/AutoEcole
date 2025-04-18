package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Facture;
import com.example.AutoEcole.dal.domain.entity.PrivateLinkUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivateLinkUsageLogRepository extends JpaRepository<PrivateLinkUsageLog, Long> {
}
