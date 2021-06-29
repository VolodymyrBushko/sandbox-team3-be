package com.exadel.discountwebapp.filter;

import com.exadel.discountwebapp.exception.exception.client.IncorrectFilterInputException;
import com.exadel.discountwebapp.exception.exception.client.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RequiredArgsConstructor
public class CustomSpecification<T> implements Specification<T> {

    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<Object> path = null;
        Class clazz = root.getJavaType();

        String key = criteria.getKey();
        String value = criteria.getValue();
        SearchOperation operation = criteria.getOperation();

        try {
            path = createPath(key, root);
        } catch (IllegalArgumentException ex) {
            throw new IncorrectFilterInputException(clazz, key, value);
        }

        query.distinct(true);

        switch (operation) {
            case EQUALITY:
                return path.getJavaType() == LocalDateTime.class
                        ? builder.equal(path.as(LocalDateTime.class), parseLocalDateTime(clazz, key, value))
                        : builder.equal(path.as(String.class), value);
            case LESS_THAN:
                return path.getJavaType() == LocalDateTime.class
                        ? builder.lessThan(path.as(LocalDateTime.class), parseLocalDateTime(clazz, key, value))
                        : builder.lessThan(path.as(String.class), value);
            case GREATER_THAN:
                return path.getJavaType() == LocalDateTime.class
                        ? builder.greaterThan(path.as(LocalDateTime.class), parseLocalDateTime(clazz, key, value))
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
            String[] split = key.split("\\.");
            String field = split[split.length - 1];
            Join<Object, Object> join = root.join(split[0], JoinType.LEFT);

            for (int i = 1; i < split.length - 1; i++) {
                join = join.join(split[i], JoinType.LEFT);
            }

            return join.get(field);
        }
        return root.get(key);
    }

    private LocalDateTime parseLocalDateTime(Class clazz, String fieldName, String value) {
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException ex) {
            throw new ParseException(clazz, fieldName, value);
        }
    }
}
