package com.exadel.discountwebapp.history.controller;

import com.exadel.discountwebapp.history.service.AuditService;
import com.exadel.discountwebapp.history.vo.AuditRequestVO;
import com.exadel.discountwebapp.history.vo.AuditResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit-operation")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @PostMapping
    public List<AuditResponseVO> auditOperation(@RequestBody AuditRequestVO request) {
        return auditService.getHistory(request);
    }
}
