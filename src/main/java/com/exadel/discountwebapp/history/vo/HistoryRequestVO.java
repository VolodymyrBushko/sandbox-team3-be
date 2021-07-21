package com.exadel.discountwebapp.history.vo;

import com.exadel.discountwebapp.history.enums.HistoryEnum;
import com.exadel.discountwebapp.history.enums.OperationsEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRequestVO {
    HistoryEnum entity;
    Long id;
    OperationsEnum operationType;
}
