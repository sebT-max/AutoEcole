package com.example.AutoEcole.dal.repository;

import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {
    Set<Document> findAllByUserId(Long userId);
    Set<Document> findAllByInscriptionId(Long inscriptionId);
//    boolean existsByInscriptionAndType(Inscription inscription, DocumentType type);

}
