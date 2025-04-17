package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Column(name = "file_url") // Spécifie explicitement le nom de la colonne
    private String fileUrl; // Le chemin vers le fichier

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
    public Document(String fileName, DocumentType type, String fileUrl, User user, Inscription inscription, LocalDateTime uploadedAt) {
        this.fileName = fileName;
        this.type = type;
        this.fileUrl = fileUrl;
        this.user = user;
        this.inscription = inscription;
        this.uploadedAt = uploadedAt;
    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Document that = (Document) o;
//        return this.getType() == that.getType()
//                && Objects.equals(this.getInscription(), that.getInscription());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getType(), getInscription());
//    }
}
