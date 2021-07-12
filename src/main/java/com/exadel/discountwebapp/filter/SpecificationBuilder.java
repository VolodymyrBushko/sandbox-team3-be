package com.exadel.discountwebapp.filter;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SpecificationBuilder<T> {

    private final List<SearchCriteria> criteria = new ArrayList<>();

    private static final String REGEXP = "(\\w+(?:\\.?\\w+)+(?:\\??))(:|<|>|\\*:|:\\*|\\*:\\*)([^(\\*)]+?);";
    private static final Pattern pattern = Pattern.compile(REGEXP);

    public Specification<T> fromQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return null;
        }

        Matcher matcher = pattern.matcher(query.trim() + ";");

        while (matcher.find()) {
            String key = matcher.group(1);
            String operation = matcher.group(2);
            String value = matcher.group(3);
            boolean orPredicate = false;

            if (key.endsWith("?")) {
                key = key.substring(0, key.length() - 1);
                orPredicate = true;
            }

            with(key, operation, value, orPredicate);
        }

        return build();
    }

    private void with(String key, String operation, String value, boolean orPredicate) {
        SearchCriteria newCriteria = SearchCriteria.builder()
                .key(key)
                .value(value)
                .operation(SearchOperation.getOperation(operation))
                .orPredicate(orPredicate)
                .build();
        criteria.add(newCriteria);
    }

    private Specification<T> build() {
        if (criteria.isEmpty()) {
            return null;
        }

        List<Specification<T>> specifications = criteria.stream()
                .map(CustomSpecification<T>::new)
                .collect(Collectors.toList());

        Specification<T> result = specifications.get(0);

        for (int i = 1; i < criteria.size(); i++) {
            result = criteria.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specifications.get(i))
                    : Specification.where(result)
                    .and(specifications.get(i));
        }

        return result;
    }
}
