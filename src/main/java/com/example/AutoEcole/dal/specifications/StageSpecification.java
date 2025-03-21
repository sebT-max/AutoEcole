package com.example.AutoEcole.dal.specifications;

import com.example.AutoEcole.dal.domain.entity.Stage;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StageSpecification {
    public static Specification<Stage> filterBy() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


        };
    }
}

