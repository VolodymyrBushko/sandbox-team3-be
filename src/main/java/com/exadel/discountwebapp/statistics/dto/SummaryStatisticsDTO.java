package com.exadel.discountwebapp.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryStatisticsDTO {
    private Map<String, Long> mostActiveUsersStats;
    private Map<String, Long> popularCategoriesStats;
    private Map<String, Long> popularVendorsStats;
    private Map<String, Long> popularDiscountsStats;
}
