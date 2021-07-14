package com.exadel.discountwebapp.statistics.controller;

import com.exadel.discountwebapp.statistics.dto.SummaryStatisticsDTO;
import com.exadel.discountwebapp.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public SummaryStatisticsDTO getStatistics(@RequestParam (value ="dataFrom") String dataFrom, @RequestParam (value ="dataTo") String dataTo) {
        return statisticsService.getStats(stringToLocalDateTime(dataFrom), stringToLocalDateTime(dataTo));
    }

    private LocalDateTime stringToLocalDateTime(String str) {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var ld = LocalDate.parse(str, formatter);
        return ld.atStartOfDay();
    }
}
