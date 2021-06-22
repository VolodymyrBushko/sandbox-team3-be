package com.exadel.discountwebapp.discount.filter;

import com.exadel.discountwebapp.discount.entity.Discount;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountSpecificationBuilder {

    private final List<SearchCriteria> criteria = new ArrayList<>();

    public DiscountSpecificationBuilder with(String key, String operation, String value) {
        SearchCriteria newCriteria = SearchCriteria.builder()
                .key(key)
                .operation(SearchOperation.getOperation(operation))
                .value(value)
                .build();
        criteria.add(newCriteria);
        return this;
    }

    public Specification<Discount> build() {
        if (criteria.size() == 0) {
            return null;
        }

        List<Specification<Discount>> specifications = criteria.stream()
                .map(DiscountSpecification::new)
                .collect(Collectors.toList());

        Specification<Discount> result = specifications.get(0);

        for (int i = 1; i < criteria.size(); i++) {
            result = Specification.where(result).and(specifications.get(i));
        }

        return result;
    }
}
