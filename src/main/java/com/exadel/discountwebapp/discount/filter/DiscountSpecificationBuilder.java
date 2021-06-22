package com.exadel.discountwebapp.discount.filter;

import com.exadel.discountwebapp.discount.entity.Discount;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class DiscountSpecificationBuilder {

    private static final List<SearchCriteria> criteria = new ArrayList<>();

    public static Specification<Discount> fromQuery(String query) {
        if (query == null || query.trim().length() == 0) {
            return null;
        }

        String regexp = "(\\w+\\.?\\w+)(:|<|>|\\*:|:\\*|\\*:\\*)([\\w%@:\\-\\.]+?);";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(query.trim() + ";");

        while (matcher.find()) {
            DiscountSpecificationBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        return DiscountSpecificationBuilder.build();
    }

    private static void with(String key, String operation, String value) {
        SearchCriteria newCriteria = SearchCriteria.builder()
                .key(key)
                .operation(SearchOperation.getOperation(operation))
                .value(value)
                .build();
        criteria.add(newCriteria);
    }

    private static Specification<Discount> build() {
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
