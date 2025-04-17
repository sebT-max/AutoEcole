package com.example.AutoEcole.dal.domain.enum_;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum InscriptionStatut {
    CONFIRME,
    EN_ATTENTE,
    ANNULE,
}
