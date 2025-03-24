package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query( "Select u " +
            "from User u " +
            "where u.email ilike :email")
    Optional<User> findByUsername(String email);

    @Query( "Select u " +
            "from User u " +
            "where u.email ilike :email")
    Optional<User> findByEmail(String email);

    @Query(
            "SELECT COUNT(*)>0 " +
                    "FROM User  " +
                    "WHERE email ILIKE :email" )
    boolean existsByEmail(String email);
}
