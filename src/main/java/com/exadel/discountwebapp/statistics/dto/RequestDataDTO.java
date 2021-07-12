package com.exadel.discountwebapp.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDataDTO {
    @NotNull
    private String dataFrom;
    @NotNull
    private String dataTo;
}
