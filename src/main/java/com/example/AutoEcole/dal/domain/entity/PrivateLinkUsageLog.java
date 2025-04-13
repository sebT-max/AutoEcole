package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class PrivateLinkUsageLog extends BaseEntity <Long> {
    private String email;

    private LocalDateTime usedAt;

    @ManyToOne
    private PrivateLink link;
}

