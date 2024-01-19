package com.floristicboom.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

import static com.floristicboom.utils.Constants.NAME;
import static com.floristicboom.utils.Constants.PRICE;

@UtilityClass
public class SearchUtility {
    public static <T> Specification<T> getDefaultSpecification(Integer minPrice, Integer maxPrice,
                                                               String partialName,
                                                               Specification<T> spec) {
        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(PRICE), BigDecimal.valueOf(minPrice)));
        }

        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(PRICE), BigDecimal.valueOf(maxPrice)));
        }

        if (partialName != null && !partialName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(NAME)), "%" + partialName.toLowerCase() + "%"));
        }
        return spec;
    }
}