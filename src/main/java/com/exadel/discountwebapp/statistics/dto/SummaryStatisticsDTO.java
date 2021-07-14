package com.exadel.discountwebapp.statistics.dto;

import com.exadel.discountwebapp.statistics.vo.CategoryVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.DiscountVO;
import com.exadel.discountwebapp.statistics.vo.VendorVO;
import com.exadel.discountwebapp.statistics.vo.uservo.UserVO;
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
    private Map<UserVO, Long> mostActiveUsersStats;
    private Map<CategoryVO, Long> popularCategoriesStats;
    private Map<VendorVO, Long> popularVendorsStats;
    private Map<DiscountVO, Long> popularDiscountsStats;
}
