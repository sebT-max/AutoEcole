package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Getter
@Setter
@Entity
public class Document extends BaseEntity<Long> {

    private String fileName;

    @Enumerated(EnumType.STRING)
    private DocumentType type;

    private String filePath; // Le chemin vers le fichier

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "inscription_id")  // Ajout de la relation avec Inscription
    private Inscription inscription;  // Lien avec l'inscription

    private LocalDateTime uploadedAt = LocalDateTime.now();

    // Constructeur par défaut pour la sérialisation JSON
    public Document() {
        // Initialisation par défaut si nécessaire
    }

    // Vous pouvez garder le constructeur avec tous les arguments si nécessaire pour d'autres usages
    public Document(String fileName, DocumentType type, String filePath, User user, Inscription inscription, LocalDateTime uploadedAt) {
        this.fileName = fileName;
        this.type = type;
        this.filePath = filePath;
        this.user = user;
        this.inscription = inscription;
        this.uploadedAt = uploadedAt;
    }
}
