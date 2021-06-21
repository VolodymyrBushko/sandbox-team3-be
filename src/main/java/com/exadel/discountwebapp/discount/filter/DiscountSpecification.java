package com.exadel.discountwebapp.discount.filter;

import com.exadel.discountwebapp.discount.entity.Discount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class DiscountSpecification implements Specification<Discount> {

    @NotNull
    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Discount> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        String key = criteria.getKey();
        String value = criteria.getValue();
        String operation = criteria.getOperation();

        Predicate predicate = null;

        switch (operation) {
            case ":":
                predicate = root.get(key).getJavaType() == String.class
                        ? builder.like(root.get(key), value)
                        : builder.equal(root.get(key), value);
                break;
            case "<":
                predicate = builder.lessThan(root.get(key), value);
                break;
            case ">":
                predicate = builder.greaterThan(root.get(key), value);
                break;
        }

        return predicate;
    }
}
