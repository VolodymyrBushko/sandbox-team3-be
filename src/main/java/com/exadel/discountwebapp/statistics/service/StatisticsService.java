package com.exadel.discountwebapp.statistics.service;

import com.exadel.discountwebapp.statistics.dto.CategoryDTO;
import com.exadel.discountwebapp.statistics.dto.SummaryStatisticsDTO;
import com.exadel.discountwebapp.statistics.dto.UserDTO;
import com.exadel.discountwebapp.statistics.dto.VendorDTO;
import com.exadel.discountwebapp.userdiscount.repository.UserDiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final UserDiscountRepository userDiscountRepository;

    private static final String OTHERS = "Others";

    public SummaryStatisticsDTO getStats(LocalDateTime dataFrom, LocalDateTime dataTo) {
        Map<String, Long> usersDiscountsStats = getDataActivatedDiscountPerUsers(dataFrom, dataTo);
        Map<String, Long> categoryDiscountsStats = getDataActivatedDiscountPerCategory(dataFrom, dataTo);
        Map<String, Long> categoryVendorStats = getDataActivatedDiscountPerVendor(dataFrom, dataTo);
        return new SummaryStatisticsDTO(usersDiscountsStats, categoryDiscountsStats, categoryVendorStats);
    }

    public Map<String, Long> getDataActivatedDiscountPerUsers(LocalDateTime dataFrom, LocalDateTime dataTo) {
        var userData = userDiscountRepository.getUserDiscountStatistics(dataFrom, dataTo);
        Map<String, Long> result = new HashMap<>();
        for (UserDTO elem : userData) {
            result.put(String.format("%s %s (%s)", elem.getFirstName(), elem.getLastName(), elem.getEmail()), elem.getQuantity());
        }
        result = result.entrySet()
                .stream()
                .sorted((v1, v2) -> (int) (v2.getValue() - v1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

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
        result = result.entrySet()
                .stream()
                .sorted((v1, v2) -> (int) (v2.getValue() - v1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

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
        var categoryData = userDiscountRepository.getVendorDiscountStatistics(dataFrom, dataTo);
        Map<String, Long> result = new HashMap<>();
        for (VendorDTO elem : categoryData) {
            result.put(String.format("%s", elem.getTitle()), elem.getQuantity());
        }
        result = result.entrySet()
                .stream()
                .sorted((v1, v2) -> (int) (v2.getValue() - v1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        var othersQuantity = categoryData.stream()
                .sorted((v1, v2) -> (int) (v2.getQuantity() - v1.getQuantity()))
                .skip(10)
                .mapToLong(VendorDTO::getQuantity)
                .sum();

        if (othersQuantity != 0L) {
            result.put(OTHERS, othersQuantity);
        }
        return result;
    }
}
