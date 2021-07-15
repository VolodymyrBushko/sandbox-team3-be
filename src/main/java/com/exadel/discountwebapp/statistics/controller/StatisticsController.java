package com.exadel.discountwebapp.statistics.controller;

import com.exadel.discountwebapp.statistics.dto.SummaryStatisticsDTO;
import com.exadel.discountwebapp.statistics.excelexport.ExcelExporter;
import com.exadel.discountwebapp.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public SummaryStatisticsDTO getStatistics(@RequestParam(value = "dataFrom") String dataFrom, @RequestParam(value = "dataTo") String dataTo) {
        return statisticsService.getStats(stringToLocalDateTime(dataFrom), stringToLocalDateTime(dataTo));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/export")
    public void getExportStatistics(HttpServletResponse response, @RequestParam(value = "dataFrom") String dataFrom, @RequestParam(value = "dataTo") String dataTo) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        var headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=statistics_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        var summaryStats = statisticsService.getStats(stringToLocalDateTime(dataFrom), stringToLocalDateTime(dataTo));
        var excelExporter = new ExcelExporter(summaryStats);
        excelExporter.export(response);
    }

    private LocalDateTime stringToLocalDateTime(String str) {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var ld = LocalDate.parse(str, formatter);
        return ld.atStartOfDay();
    }
}
