package com.exadel.discountwebapp.statistics.controller;

import com.exadel.discountwebapp.statistics.dto.SummaryStatistics;
import com.exadel.discountwebapp.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

  private final StatisticsService statisticsService;

  @GetMapping
    public SummaryStatistics getStatistics(){
        statisticsService.getDataActivatedDiscountPerUsers();
        return null;
    }
}
