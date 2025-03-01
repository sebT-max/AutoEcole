package com.example.AutoEcole.api.model.Paiement;

import com.example.AutoEcole.dal.domain.entity.UniversalId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePaiementResponseBody {
    private Long id;
    private String universalNumber;
    private String placeOfIssue;
    private LocalDate birthDate;
    private LocalDate expiryDate;

    public CreatePaiementResponseBody(Long id, String universalNumber, String placeOfIssue,
                                      LocalDate birthDate, LocalDate expiryDate){
        this.id = id;
        this.universalNumber = universalNumber;
        this.placeOfIssue = placeOfIssue;
        this.birthDate = birthDate;
        this.expiryDate = expiryDate;
    }

    public static CreatePaiementResponseBody fromEntity(UniversalId universalId){
        return new CreatePaiementResponseBody(
                universalId.getId(),
                universalId.getUniversalNumber(),
                universalId.getPlaceOfIssue(),
                universalId.getBirthDate(),
                universalId.getExpiryDate()
        );
    }

}
