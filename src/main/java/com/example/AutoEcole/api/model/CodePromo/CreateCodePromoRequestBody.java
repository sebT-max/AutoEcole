package com.example.AutoEcole.api.model.CodePromo;

import java.time.LocalDate;

public record CreateCodePromoRequestBody(
        String code,
        Integer reduction,
        LocalDate expiry_date
        ) {
}
