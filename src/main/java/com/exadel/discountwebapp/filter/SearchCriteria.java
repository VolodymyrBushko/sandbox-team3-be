package com.exadel.discountwebapp.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria implements Serializable {
    private String key;
    private String value;
    private SearchOperation operation;
    private boolean orPredicate;
}
