package com.exadel.discountwebapp.statistics.service;

import com.exadel.discountwebapp.statistics.dto.UserDTO;
import com.exadel.discountwebapp.statistics.dto.UserDiscountCountDTO;
import com.exadel.discountwebapp.user.repository.UserDiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticsService {
    private final UserDiscountRepository userDiscountRepository;

    public Map<UserDTO, Long> getDataActivatedDiscountPerUsers() {
        var userData = userDiscountRepository.getUserDiscountStatistics();

        Map<UserDTO, Long> result = userData. stream()
                .collect(
                        Collectors.groupingBy(UserDiscountCountDTO::getDto , Collectors.counting()));
//        result.entrySet().stream()
//                .sorted((u1, u2 )->u1.getKey() )

        return result;
    }
}
