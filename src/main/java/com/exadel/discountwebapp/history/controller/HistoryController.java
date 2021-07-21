package com.exadel.discountwebapp.history.controller;

import com.exadel.discountwebapp.history.service.HistoryService;
import com.exadel.discountwebapp.history.vo.HistoryRequestVO;
import com.exadel.discountwebapp.history.vo.HistoryResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit_versioning")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @SneakyThrows
    @PostMapping
    public List<HistoryResponseVO> getDifferenceByEntityId(@RequestBody HistoryRequestVO request) {
        return historyService.getHistory(request);
    }
}
