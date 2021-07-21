package com.exadel.discountwebapp.history.vo;

import com.exadel.discountwebapp.history.enums.AuditEntityType;
import com.exadel.discountwebapp.history.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditRequestVO {
    private AuditEntityType entity;
    private Long id;
    private OperationType operationType;
}
