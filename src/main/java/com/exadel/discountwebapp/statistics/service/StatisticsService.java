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
    public SummaryStatisticsDTO getStats(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<UserVO> usersDiscountsStats = getDataActivatedDiscountPerUsers(dateFrom, dateTo);
        List<CategoryVO> categoryDiscountsStats = getDataActivatedDiscountPerCategory(dateFrom, dateTo);
        List<VendorVO> categoryVendorStats = getDataActivatedDiscountPerVendor(dateFrom, dateTo);
        List<DiscountVO> discountViewingStats = getDiscountPerViewing();
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

        List<ExtendedUserVO> result = new ArrayList<>();
        for (ExtendedUserDTO elem : extendedUserData) {
            result.add(statsMapper.userToVO(elem));
        }
        return result.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .collect(Collectors.toList());
    }

    public List<ExtendedCategoryVO> getExtendedDataActivatedDiscountPerCategory(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var categoryData = userDiscountRepository.getExtendedCategoryDiscountStatistics(dateFrom, dateTo);

        List<ExtendedCategoryVO> result = new ArrayList<>();
        for (ExtendedCategoryDTO elem : categoryData) {
            result.add(statsMapper.categoryToVO(elem));
        }
        return result.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .collect(Collectors.toList());
    }

    public List<ExtendedVendorVO> getExtendedDataActivatedDiscountPerVendor(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var vendorData = userDiscountRepository.getExtendedVendorDiscountStatistics(dateFrom, dateTo);

        List<ExtendedVendorVO> result = new ArrayList<>();
        for (ExtendedVendorDTO elem : vendorData) {
            result.add(statsMapper.vendorToVO(elem));
        }

        return result.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .collect(Collectors.toList());
    }

    public List<ExtendedDiscountVO> getExtendedDiscountsStats() {
        var disViewsData = discountRepository.getExtendedDiscountSummary();

        List<ExtendedDiscountVO> result = new ArrayList<>();

        for (ExtendedDiscountViewsDTO elem : disViewsData) {
            if (elem.getViewNumber() != null)
                result.add(statsMapper.discountToVO(elem));
        }

        return result.stream()
                .sorted((v1, v2) -> (int) (v2.getViewNumber() - v1.getViewNumber()))
                .collect(Collectors.toList());
    }

    public List<UserVO> getDataActivatedDiscountPerUsers(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var userData = userDiscountRepository.getUserDiscountStatistics(dateFrom, dateTo);

        List<UserVO> result = new ArrayList<>();
        for (UserDTO elem : userData) {
            result.add(new UserVO(elem.getId(), elem.getFirstName(), elem.getLastName(), elem.getEmail(), elem.getQuantity()));
        }

        result = result.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .limit(5)
                .collect(Collectors.toList());


        var othersQuantity = userData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(5)
                .mapToLong(UserDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.add(new OthersUsersVO(OTHERS, othersQuantity));
        }
        return result;
    }

    public List<CategoryVO> getDataActivatedDiscountPerCategory(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var categoryData = userDiscountRepository.getCategoryDiscountStatistics(dateFrom, dateTo);

        List<CategoryVO> result = new ArrayList<>();
        for (CategoryDTO elem : categoryData) {
            result.add(new CategoryVO(elem.getId(), elem.getTitle(), elem.getQuantity()));
        }

        result = result.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .limit(5)
                .collect(Collectors.toList());

        var othersQuantity = categoryData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(5)
                .mapToLong(CategoryDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.add(new OthersCategoriesVO(OTHERS, othersQuantity));
        }
        return result;
    }

    public List<VendorVO> getDataActivatedDiscountPerVendor(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var vendorData = userDiscountRepository.getVendorDiscountStatistics(dateFrom, dateTo);

        List<VendorVO> result = new ArrayList<>();
        for (VendorDTO elem : vendorData) {
            result.add(new VendorVO(elem.getId(), elem.getTitle(), elem.getQuantity()));
        }

        result = result.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .limit(5)
                .collect(Collectors.toList());

        var othersQuantity = vendorData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(5)
                .mapToLong(VendorDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.add(new OthersVendorsVO(OTHERS, othersQuantity));
        }
        return result;
    }

    public List<DiscountVO> getDiscountPerViewing() {
        var disViewsData = discountRepository.getDiscountSummary();

        List<DiscountVO> result = new ArrayList<>();

        for (DiscountViewingDTO elem : disViewsData) {
            if (elem.getQuantity() != null)
                result.add(new DiscountVO(elem.getId(), elem.getTitle(), elem.getQuantity()));
        }

        result = result.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .limit(5)
                .collect(Collectors.toList());

        var othersQuantity = disViewsData.stream()
                .filter(e -> e.getQuantity() != null)
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(5)
                .mapToLong(DiscountViewingDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.add(new OthersDiscountsVO(OTHERS, othersQuantity));
        }
        return result;
    }
}
