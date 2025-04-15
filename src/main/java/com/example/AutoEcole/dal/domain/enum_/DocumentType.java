package com.example.AutoEcole.dal.domain.enum_;

public enum DocumentType {
    PERMIS_RECTO,
    PERMIS_VERSO,
    PIECE_IDENTITE_RECTO,
    PIECE_IDENTITE_VERSO,
    LETTRE_48N;

    public String getLabel() {
        switch (this) {
            case PERMIS_RECTO: return "Permis Recto";
            case PERMIS_VERSO: return "Permis Verso";
            case PIECE_IDENTITE_RECTO: return "Pièce d'Identité Recto";
            case PIECE_IDENTITE_VERSO: return "Pièce d'Identité Verso";
            case LETTRE_48N: return "Lettre 48N";
            default: throw new IllegalArgumentException("Type de document non connu");
        }
    }
}

