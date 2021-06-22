package com.exadel.discountwebapp.discount.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CustomSpecification<T> implements Specification<T> {

    @NotNull
    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        String key = criteria.getKey();
        String value = criteria.getValue();
        SearchOperation operation = criteria.getOperation();

        Path<Object> path = createPath(key, root);

        switch (operation) {
            case EQUALITY:
                return path.getJavaType() == LocalDateTime.class
                        ? builder.equal(path.as(LocalDateTime.class), LocalDateTime.parse(value))
                        : builder.equal(path.as(String.class), value);
            case LESS_THAN:
                return path.getJavaType() == LocalDateTime.class
                        ? builder.lessThan(path.as(LocalDateTime.class), LocalDateTime.parse(value))
                        : builder.lessThan(path.as(String.class), value);
            case GREATER_THAN:
                return path.getJavaType() == LocalDateTime.class
                        ? builder.greaterThan(path.as(LocalDateTime.class), LocalDateTime.parse(value))
                        : builder.greaterThan(path.as(String.class), value);
            case STARTS_WITH:
                return builder.like(path.as(String.class), value + "%");
            case ENDS_WITH:
                return builder.like(path.as(String.class), "%" + value);
            case CONTAINS:
                return builder.like(path.as(String.class), "%" + value + "%");
            default:
                return null;
        }
    }

    private <X, Y> Path<X> createPath(String key, Root<Y> root) {
        if (key.contains(".")) {
            String[] pairs = key.split("\\.");
            return root.join(pairs[0]).get(pairs[1]);
        }
        return root.get(key);
    }
}
