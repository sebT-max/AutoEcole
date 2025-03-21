package com.example.AutoEcole.dal.specifications;

import com.example.AutoEcole.dal.domain.entity.Stage;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StageSpecification {
    public static Specification<Stage> filterBy(String entreprise, String localisation, Integer duree, LocalDate dateDebut) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (entreprise != null) {
                predicates.add(cb.equal(root.get("entreprise"), entreprise));
            }

            if (localisation != null) {
                predicates.add(cb.equal(root.get("localisation"), localisation));
            }

            if (duree != null) {
                predicates.add(cb.equal(root.get("duree"), duree));
            }

            if (dateDebut != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateDebut"), dateDebut));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

