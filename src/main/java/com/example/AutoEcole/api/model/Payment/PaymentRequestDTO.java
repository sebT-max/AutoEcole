package com.example.AutoEcole.api.model.Payment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PaymentRequestDTO {
    private Long inscriptionId;
    private Long stageId;
    private Long amount;
    private String productName;
    private String codePromo;
}
