package com.exadel.discountwebapp.discount.filter;

import com.exadel.discountwebapp.discount.entity.Discount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
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
        Path<String> path = createPath(key, root);

        switch (operation) {
            case ":":
                predicate = builder.equal(path, value);
                break;
            case "*:":
                predicate = builder.like(path, "%" + value);
                break;
            case ":*":
                predicate = builder.like(path, value + "%");
                break;
            case "*:*":
                predicate = builder.like(path, "%" + value + "%");
                break;
            case "<":
                predicate = builder.lessThan(path, value);
                break;
            case ">":
                predicate = builder.greaterThan(path, value);
                break;
        }

        return predicate;
    }

    private <Y> Path<String> createPath(String key, Root<Y> root) {
        if (key.contains(".")) {
            String[] pairs = key.split("\\.");
            return root.join(pairs[0]).get(pairs[1]);
        }
        return root.get(key);
    }
}
