package com.exadel.discountwebapp.statistics.extendedvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedUserVO {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String city;
    private String role;
    private Long quantity;
}
