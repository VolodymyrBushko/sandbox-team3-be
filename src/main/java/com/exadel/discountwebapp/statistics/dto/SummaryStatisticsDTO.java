package com.exadel.discountwebapp.statistics.dto;

import com.exadel.discountwebapp.statistics.vo.CategoryVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.DiscountVO;
import com.exadel.discountwebapp.statistics.vo.vendorvo.VendorVO;
import com.exadel.discountwebapp.statistics.vo.uservo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryStatisticsDTO {
    private List<UserVO> mostActiveUsersStats;
    private List<CategoryVO> popularCategoriesStats;
    private List<VendorVO> popularVendorsStats;
    private List<DiscountVO> popularDiscountsStats;
}
