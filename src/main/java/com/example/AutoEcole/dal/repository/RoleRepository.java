package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.name = :name")
    boolean existRoleByName(String name);

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findRoleByName(String name);

}
