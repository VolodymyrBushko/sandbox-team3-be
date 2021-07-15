package com.exadel.discountwebapp.statistics.service;

import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.statistics.dto.*;
import com.exadel.discountwebapp.statistics.vo.CategoryVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.DiscountVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.OthersDiscountsVO;
import com.exadel.discountwebapp.statistics.vo.uservo.OthersUsersVO;
import com.exadel.discountwebapp.statistics.vo.uservo.UserVO;
import com.exadel.discountwebapp.statistics.vo.VendorVO;
import com.exadel.discountwebapp.userdiscount.repository.UserDiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final UserDiscountRepository userDiscountRepository;
    private final DiscountRepository discountRepository;

    private static final String OTHERS = "Others";

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public SummaryStatisticsDTO getStats(LocalDateTime dateFrom, LocalDateTime dateTo) {
        Map<UserVO, Long> usersDiscountsStats = getDataActivatedDiscountPerUsers(dateFrom, dateTo);
        Map<CategoryVO, Long> categoryDiscountsStats = getDataActivatedDiscountPerCategory(dateFrom, dateTo);
        Map<VendorVO, Long> categoryVendorStats = getDataActivatedDiscountPerVendor(dateFrom, dateTo);
        Map<DiscountVO, Long> discountViewingStats = getDiscountPerViewing();
        return new SummaryStatisticsDTO(usersDiscountsStats, categoryDiscountsStats, categoryVendorStats, discountViewingStats);
    }

    @SuppressWarnings("unchecked")
    public Map<UserVO, Long> getDataActivatedDiscountPerUsers(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var userData = userDiscountRepository.getUserDiscountStatistics(dateFrom, dateTo);

        Map<UserVO, Long> result = new HashMap<>();
        for (UserDTO elem : userData) {
            result.put(new UserVO(elem.getFirstName(), elem.getLastName(), elem.getEmail()), elem.getQuantity());
        }

        result = (Map<UserVO, Long>) sortingMap(result);

        var othersQuantity = userData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(UserDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(new OthersUsersVO(OTHERS), othersQuantity);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public Map<CategoryVO, Long> getDataActivatedDiscountPerCategory(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var categoryData = userDiscountRepository.getCategoryDiscountStatistics(dateFrom, dateTo);

        Map<CategoryVO, Long> result = new HashMap<>();
        for (CategoryDTO elem : categoryData) {
            result.put(new CategoryVO(elem.getTitle()), elem.getQuantity());
        }

        result = (Map<CategoryVO, Long>) sortingMap(result);

        var othersQuantity = categoryData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(CategoryDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(new CategoryVO(OTHERS), othersQuantity);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public Map<VendorVO, Long> getDataActivatedDiscountPerVendor(LocalDateTime dateFrom, LocalDateTime dateTo) {
        var vendorData = userDiscountRepository.getVendorDiscountStatistics(dateFrom, dateTo);

        Map<VendorVO, Long> result = new HashMap<>();
        for (VendorDTO elem : vendorData) {
            result.put(new VendorVO(elem.getId(), elem.getTitle()), elem.getQuantity());
        }

        result = (Map<VendorVO, Long>) sortingMap(result);

        var othersQuantity = vendorData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(VendorDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(new VendorVO(OTHERS), othersQuantity);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public Map<DiscountVO, Long> getDiscountPerViewing() {
        var disViewsData = discountRepository.getDiscountSummary();

        Map<DiscountVO, Long> result = new HashMap<>();
        for (DiscountViewingDTO elem : disViewsData) {
            result.put(new DiscountVO(elem.getId(), elem.getTitle()), elem.getQuantity());
        }

        result = (Map<DiscountVO, Long>) sortingMap(result);

        var othersQuantity = disViewsData.stream()
                .filter(e -> e.getQuantity() != null)
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(DiscountViewingDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(new OthersDiscountsVO(OTHERS), othersQuantity);
        }
        return result;
    }

    private Map<?, Long> sortingMap(Map<?, Long> data) {
        return data.entrySet()
                .stream()
                .filter(v -> v.getValue() != null)
                .sorted((v1, v2) -> (int) (v2.getValue() - v1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
