package com.exadel.discountwebapp.statistics.controller;

import com.exadel.discountwebapp.statistics.dto.SummaryStatisticsDTO;
import com.exadel.discountwebapp.statistics.excelexport.XLSXExported;
import com.exadel.discountwebapp.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public SummaryStatisticsDTO getStatistics(@RequestParam(value = "dateFrom") @DateTimeFormat(pattern = "dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                              @RequestParam(value = "dateTo") @DateTimeFormat(pattern = "dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
        return statisticsService.getStats(dateFrom.atStartOfDay(), dateTo.atStartOfDay().plusHours(24));
    }

    @SneakyThrows
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getExportExtendedStatistics(HttpServletResponse response, @RequestParam(value = "dateFrom") @DateTimeFormat(pattern = "dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                            @RequestParam(value = "dateTo") @DateTimeFormat(pattern = "dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        var headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=extended_statistics_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        var extendedStats = statisticsService.getExtendedStats(dateFrom.atStartOfDay(), dateTo.atStartOfDay().plusHours(24));
        var xlsxExported = new XLSXExported(extendedStats);
        xlsxExported.export(response.getOutputStream());
    }
}
