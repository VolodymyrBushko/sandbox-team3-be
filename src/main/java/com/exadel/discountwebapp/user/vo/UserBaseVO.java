package com.exadel.discountwebapp.user.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseVO implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
