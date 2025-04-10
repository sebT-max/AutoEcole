package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {

}
