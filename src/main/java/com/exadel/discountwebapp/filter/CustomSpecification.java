package com.exadel.discountwebapp.filter;

import com.exadel.discountwebapp.exception.exception.client.IncorrectFilterInputException;
import com.exadel.discountwebapp.exception.exception.client.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RequiredArgsConstructor
public class CustomSpecification<T> implements Specification<T> {

    private static final String NULL_VALUE = "null";

    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<Object> path;
        Class clazz = root.getJavaType();

        String key = criteria.getKey();
        String value = criteria.getValue().toLowerCase();
        SearchOperation operation = criteria.getOperation();

        try {
            path = createPath(key, root);
        } catch (IllegalArgumentException ex) {
            throw new IncorrectFilterInputException(clazz, key, value);
        }

        query.distinct(true);

        switch (operation) {
            case EQUALITY:
                if (path.getJavaType().equals(LocalDateTime.class)) {
                    return builder.equal(path.as(LocalDateTime.class), parseLocalDateTime(clazz, key, value));
                } else if (value.equalsIgnoreCase(NULL_VALUE)) {
                    return builder.isNull(path);
                } else {
                    return builder.equal(builder.lower(path.as(String.class)), value);
                }
            case NOT_EQUALITY:
                if (path.getJavaType().equals(LocalDateTime.class)) {
                    return builder.notEqual(path.as(LocalDateTime.class), parseLocalDateTime(clazz, key, value));
                } else if (value.equalsIgnoreCase(NULL_VALUE)) {
                    return builder.isNotNull(path);
                } else {
                    return builder.notEqual(builder.lower(path.as(String.class)), value);
                }
            case LESS_THAN:
                return path.getJavaType().equals(LocalDateTime.class)
                        ? builder.lessThan(path.as(LocalDateTime.class), parseLocalDateTime(clazz, key, value))
                        : builder.lessThan(builder.lower(path.as(String.class)), value);
            case GREATER_THAN:
                return path.getJavaType().equals(LocalDateTime.class)
                        ? builder.greaterThan(path.as(LocalDateTime.class), parseLocalDateTime(clazz, key, value))
                        : builder.greaterThan(builder.lower(path.as(String.class)), value);
            case STARTS_WITH:
                return builder.like(builder.lower(path.as(String.class)), value + "%");
            case ENDS_WITH:
                return builder.like(builder.lower(path.as(String.class)), "%" + value);
            case CONTAINS:
                return builder.like(builder.lower(path.as(String.class)), "%" + value + "%");
            case IN:
                List<String> arguments = List.of(value.split(","));
                return builder.lower(path.as(String.class)).in(arguments);
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
