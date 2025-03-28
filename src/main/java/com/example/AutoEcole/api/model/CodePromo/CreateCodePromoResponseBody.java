package com.example.AutoEcole.api.model.CodePromo;

import java.time.LocalDate;

public record CreateCodePromoResponseBody(
        String code,
        Integer reduction,
        LocalDate expiry_date
) {
}
