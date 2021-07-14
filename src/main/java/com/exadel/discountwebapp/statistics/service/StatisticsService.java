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
    public SummaryStatisticsDTO getStats(LocalDateTime dataFrom, LocalDateTime dataTo) {
        Map<UserVO, Long> usersDiscountsStats = getDataActivatedDiscountPerUsers(dataFrom, dataTo);
        Map<CategoryVO, Long> categoryDiscountsStats = getDataActivatedDiscountPerCategory(dataFrom, dataTo);
        Map<VendorVO, Long> categoryVendorStats = getDataActivatedDiscountPerVendor(dataFrom, dataTo);
        Map<DiscountVO, Long> discountViewingStats = getDiscountPerViewing(dataFrom, dataTo);
        return new SummaryStatisticsDTO(usersDiscountsStats, categoryDiscountsStats, categoryVendorStats, discountViewingStats);
    }

    @SuppressWarnings("unchecked")
    public Map<UserVO, Long> getDataActivatedDiscountPerUsers(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var userData = userDiscountRepository.getUserDiscountStatistics(dataFrom, dataTo);

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
    public Map<CategoryVO, Long> getDataActivatedDiscountPerCategory(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var categoryData = userDiscountRepository.getCategoryDiscountStatistics(dataFrom, dataTo);

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
    public Map<VendorVO, Long> getDataActivatedDiscountPerVendor(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var vendorData = userDiscountRepository.getVendorDiscountStatistics(dataFrom, dataTo);

        Map<VendorVO, Long> result = new HashMap<>();
        for (VendorDTO elem : vendorData) {
            result.put(new VendorVO(elem.getTitle()), elem.getQuantity());
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
    public Map<DiscountVO, Long> getDiscountPerViewing(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var disViewsData = discountRepository.getDiscountViewing(dataFrom, dataTo);

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
