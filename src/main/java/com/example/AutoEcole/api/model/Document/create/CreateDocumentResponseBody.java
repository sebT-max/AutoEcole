package com.example.AutoEcole.api.model.Document.create;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;

public record CreateDocumentResponseBody<A extends BaseEntity<Long>>(
        String message,
        String name,
        String description,
        Double price
) {

}
