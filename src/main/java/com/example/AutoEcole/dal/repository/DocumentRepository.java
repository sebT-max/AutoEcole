package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {

    List<Document> findAllByUserId(Long userId);

    List<Document> findAllByInscriptionId(Long inscriptionId);
}
