package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existUserByEmail(String email);

//    @Query( "Select u " +
//            "from User u " +
//            "where u.email ilike :username")
//    Optional<User> findByUsername(String username);

    @Query( "Select u " +
            "from User u " +
            "where u.email ilike :email")
    Optional<User> findByEmail(String email);

}
