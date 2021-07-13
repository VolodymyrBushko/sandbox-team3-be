package com.exadel.discountwebapp.statistics.service;

import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.statistics.dto.*;
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
        Map<String, Long> usersDiscountsStats = getDataActivatedDiscountPerUsers(dataFrom, dataTo);
        Map<String, Long> categoryDiscountsStats = getDataActivatedDiscountPerCategory(dataFrom, dataTo);
        Map<String, Long> categoryVendorStats = getDataActivatedDiscountPerVendor(dataFrom, dataTo);
        Map<String, Long> discountViewingStats = getDiscountPerViewing(dataFrom, dataTo);
        return new SummaryStatisticsDTO(usersDiscountsStats, categoryDiscountsStats, categoryVendorStats, discountViewingStats);
    }

    public Map<String, Long> getDataActivatedDiscountPerUsers(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var userData = userDiscountRepository.getUserDiscountStatistics(dataFrom, dataTo);
        Map<String, Long> result = new HashMap<>();
        for (UserDTO elem : userData) {
            result.put(String.format("%s %s, %s", elem.getFirstName(), elem.getLastName(), elem.getEmail()), elem.getQuantity());
        }
        result = sortingMap(result);

        var othersQuantity = userData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(UserDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(OTHERS, othersQuantity);
        }
        return result;
    }

    public Map<String, Long> getDataActivatedDiscountPerCategory(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var categoryData = userDiscountRepository.getCategoryDiscountStatistics(dataFrom, dataTo);
        Map<String, Long> result = new HashMap<>();
        for (CategoryDTO elem : categoryData) {
            result.put(String.format("%s", elem.getTitle()), elem.getQuantity());
        }

        result = sortingMap(result);

        var othersQuantity = categoryData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(CategoryDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(OTHERS, othersQuantity);
        }
        return result;
    }

    public Map<String, Long> getDataActivatedDiscountPerVendor(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var vendorData = userDiscountRepository.getVendorDiscountStatistics(dataFrom, dataTo);
        Map<String, Long> result = new HashMap<>();
        for (VendorDTO elem : vendorData) {
            result.put(String.format("%s", elem.getTitle()), elem.getQuantity());
        }

        result = sortingMap(result);

        var othersQuantity = vendorData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(VendorDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(OTHERS, othersQuantity);
        }
        return result;
    }

    public Map<String, Long> getDiscountPerViewing(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var disViewsData = discountRepository.getDiscountViewing(dataFrom, dataTo);

        Map<String, Long> result = new HashMap<>();
        for (DiscountViewingDTO elem : disViewsData) {
            result.put(String.format("%d, %s", elem.getId(), elem.getTitle()), elem.getQuantity());
        }

        result = result.entrySet()
                .stream()
                .filter(v -> v.getValue() != null)
                .sorted((v1, v2) -> (int) (v2.getValue() - v1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        var othersQuantity = disViewsData.stream()
                .filter(e -> e.getQuantity() != null)
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(DiscountViewingDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(OTHERS, othersQuantity);
        }
        return result;
    }

    private Map<String, Long> sortingMap(Map<String, Long> data) {
        return data.entrySet()
                .stream()
                .sorted((v1, v2) -> (int) (v2.getValue() - v1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
