package com.example.AutoEcole.dal.domain.enum_;

public enum DocumentType {
    PERMIS,
    PIECE_IDENTITE,
    LETTRE_48N;

    public String getLabel() {
        switch (this) {
            case PERMIS: return "Permis";
            case PIECE_IDENTITE: return "Pièce d'Identité";
            case LETTRE_48N: return "Lettre 48N";
            default: throw new IllegalArgumentException("Type de document non connu");
        }
    }
}

