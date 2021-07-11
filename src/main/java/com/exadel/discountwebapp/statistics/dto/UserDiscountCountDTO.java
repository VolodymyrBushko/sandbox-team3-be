package com.exadel.discountwebapp.statistics.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDiscountCountDTO implements Comparable<UserDiscountCountDTO>{
   UserDTO dto;

    @Override
    public int compareTo(@NotNull UserDiscountCountDTO o) {
        return this.dto.getEmail().compareTo(o.dto.getEmail());
    }

}
