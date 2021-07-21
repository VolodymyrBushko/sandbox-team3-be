package com.exadel.discountwebapp.statistics.dto.extendeddto;

import com.exadel.discountwebapp.statistics.extendedvo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedSummaryStatsDTO {
    private List<ExtendedUserVO> extendedUsersStats;
    private List<ExtendedCategoryVO> extendedCategoriesStats;
    private List<ExtendedVendorVO> extendedVendorsStats;
    private List<ExtendedDiscountVO> extendedDiscountsStats;
    private List<ExtendedUsersPreferenceVO> extendedUsersPreference;
}
