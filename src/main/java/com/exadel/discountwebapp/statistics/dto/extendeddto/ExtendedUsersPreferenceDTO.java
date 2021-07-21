package com.exadel.discountwebapp.statistics.dto.extendeddto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedUsersPreferenceDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String city;
    private String role;
    private String category;
    private long quantity;
}
