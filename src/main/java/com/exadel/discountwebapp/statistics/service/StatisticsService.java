package com.exadel.discountwebapp.statistics.service;

import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.statistics.dto.*;
import com.exadel.discountwebapp.statistics.dto.extendeddto.*;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedCategoryVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedDiscountVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedUserVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedVendorVO;
import com.exadel.discountwebapp.statistics.mapper.*;
import com.exadel.discountwebapp.statistics.vo.categoryvo.CategoryVO;
import com.exadel.discountwebapp.statistics.vo.categoryvo.OthersCategoriesVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.DiscountVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.OthersDiscountsVO;
import com.exadel.discountwebapp.statistics.vo.uservo.OthersUsersVO;
import com.exadel.discountwebapp.statistics.vo.uservo.UserVO;
import com.exadel.discountwebapp.statistics.vo.vendorvo.OthersVendorsVO;
import com.exadel.discountwebapp.statistics.vo.vendorvo.VendorVO;
import com.exadel.discountwebapp.userdiscount.repository.UserDiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final UserDiscountRepository userDiscountRepository;
    private final DiscountRepository discountRepository;
    private final StatsMapper statsMapper;

    private static final String OTHERS = "Others";

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public SummaryStatisticsDTO getStats(LocalDateTime dateFrom, LocalDateTime dateTo, Integer range) {
        List<UserVO> usersDiscountsStats = getDataActivatedDiscountPerUsers(dateFrom, dateTo, range);
        List<CategoryVO> categoryDiscountsStats = getDataActivatedDiscountPerCategory(dateFrom, dateTo, range);
        List<VendorVO> categoryVendorStats = getDataActivatedDiscountPerVendor(dateFrom, dateTo, range);
        List<DiscountVO> discountViewingStats = getDiscountPerViewing(range);
        return new SummaryStatisticsDTO(usersDiscountsStats, categoryDiscountsStats, categoryVendorStats, discountViewingStats);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ExtendedSummaryStatsDTO getExtendedStats(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<ExtendedUserVO> extendedUsersStats = getExtendedDataDiscountPerUsers(dateFrom, dateTo);
        List<ExtendedCategoryVO> extendedCategoriesStats = getExtendedDataActivatedDiscountPerCategory(dateFrom, dateTo);
        List<ExtendedVendorVO> extendedVendorsStats = getExtendedDataActivatedDiscountPerVendor(dateFrom, dateTo);
        List<ExtendedDiscountVO> extendedDiscountsStats = getExtendedDiscountsStats();
        return new ExtendedSummaryStatsDTO(extendedUsersStats, extendedCategoriesStats, extendedVendorsStats, extendedDiscountsStats);
    }


    public List<ExtendedUserVO> getExtendedDataDiscountPerUsers(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var extendedUserData = userDiscountRepository.getExtendedUserDiscountStatistics(dateFrom, dateTo);

        return extendedUserData.stream()
                .map(statsMapper::userToVO)
                .sorted(Comparator.comparing(ExtendedUserVO::getQuantity).reversed())
                .collect(Collectors.toList());
    }

    public List<ExtendedCategoryVO> getExtendedDataActivatedDiscountPerCategory(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var categoryData = userDiscountRepository.getExtendedCategoryDiscountStatistics(dateFrom, dateTo);

        return categoryData.stream()
                .map(statsMapper::categoryToVO)
                .sorted(Comparator.comparing(ExtendedCategoryVO::getQuantity).reversed())
                .collect(Collectors.toList());
    }

    public List<ExtendedVendorVO> getExtendedDataActivatedDiscountPerVendor(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var vendorData = userDiscountRepository.getExtendedVendorDiscountStatistics(dateFrom, dateTo);

        return vendorData.stream()
                .map(statsMapper::vendorToVO)
                .sorted(Comparator.comparing(ExtendedVendorVO::getQuantity).reversed())
                .collect(Collectors.toList());
    }

    public List<ExtendedDiscountVO> getExtendedDiscountsStats() {
        var disViewsData = discountRepository.getExtendedDiscountSummary();

        return disViewsData.stream()
                .filter(e -> e.getViewNumber() != null)
                .map(statsMapper::discountToVO)
                .sorted(Comparator.comparing(ExtendedDiscountVO::getViewNumber).reversed())
                .collect(Collectors.toList());
    }

    public List<UserVO> getDataActivatedDiscountPerUsers(LocalDateTime dateFrom, LocalDateTime dateTo, Integer range) {
        var userData = userDiscountRepository.getUserDiscountStatistics(dateFrom, dateTo);

        List<UserVO> result = new ArrayList<>();
        for (UserDTO elem : userData) {
            result.add(new UserVO(elem.getId(), elem.getFirstName(), elem.getLastName(), elem.getEmail(), elem.getQuantity()));
        }

        result = result.stream()
                .sorted(Comparator.comparing(UserVO::getQuantity).reversed())
                .limit(range)
                .collect(Collectors.toList());


        var othersQuantity = userData.stream()
                .sorted(Comparator.comparing(UserDTO::getQuantity).reversed())
                .skip(range)
                .mapToLong(UserDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.add(new OthersUsersVO(OTHERS, othersQuantity));
        }
        return result;
    }

    public List<CategoryVO> getDataActivatedDiscountPerCategory(LocalDateTime dateFrom, LocalDateTime dateTo, Integer range) {
        var categoryData = userDiscountRepository.getCategoryDiscountStatistics(dateFrom, dateTo);

        List<CategoryVO> result = new ArrayList<>();
        for (CategoryDTO elem : categoryData) {
            result.add(new CategoryVO(elem.getId(), elem.getTitle(), elem.getQuantity()));
        }

        result = result.stream()
                .sorted(Comparator.comparing(CategoryVO::getQuantity).reversed())
                .limit(range)
                .collect(Collectors.toList());

        var othersQuantity = categoryData.stream()
                .sorted(Comparator.comparing(CategoryDTO::getQuantity).reversed())
                .skip(range)
                .mapToLong(CategoryDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.add(new OthersCategoriesVO(OTHERS, othersQuantity));
        }
        return result;
    }

    public List<VendorVO> getDataActivatedDiscountPerVendor(LocalDateTime dateFrom, LocalDateTime dateTo, Integer range) {
        var vendorData = userDiscountRepository.getVendorDiscountStatistics(dateFrom, dateTo);

        List<VendorVO> result = new ArrayList<>();
        for (VendorDTO elem : vendorData) {
            result.add(new VendorVO(elem.getId(), elem.getTitle(), elem.getQuantity()));
        }

        result = result.stream()
                .sorted(Comparator.comparing(VendorVO::getQuantity).reversed())
                .limit(range)
                .collect(Collectors.toList());

        var othersQuantity = vendorData.stream()
                .sorted(Comparator.comparing(VendorDTO::getQuantity).reversed())
                .skip(range)
                .mapToLong(VendorDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.add(new OthersVendorsVO(OTHERS, othersQuantity));
        }
        return result;
    }

    public List<DiscountVO> getDiscountPerViewing(Integer range) {
        var disViewsData = discountRepository.getDiscountSummary();

        List<DiscountVO> result = new ArrayList<>();

        for (DiscountViewingDTO elem : disViewsData) {
            if (elem.getQuantity() != null)
                result.add(new DiscountVO(elem.getId(), elem.getTitle(), elem.getQuantity()));
        }

        result = result.stream()
                .sorted(Comparator.comparing(DiscountVO::getQuantity).reversed())
                .limit(range)
                .collect(Collectors.toList());

        var othersQuantity = disViewsData.stream()
                .filter(e -> e.getQuantity() != null)
                .sorted(Comparator.comparing(DiscountViewingDTO::getQuantity).reversed())
                .skip(range)
                .mapToLong(DiscountViewingDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.add(new OthersDiscountsVO(OTHERS, othersQuantity));
        }
        return result;
    }
}
