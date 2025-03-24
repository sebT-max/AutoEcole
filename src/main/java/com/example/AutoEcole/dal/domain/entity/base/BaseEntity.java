package com.example.AutoEcole.dal.domain.entity.base;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@MappedSuperclass
@Getter
public abstract class BaseEntity<T extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Setter
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT",nullable = false )
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Setter
    private LocalDateTime updatedAt;

    /*
    public abstract Collection<? extends GrantedAuthority> getAuthorities();

     */
}