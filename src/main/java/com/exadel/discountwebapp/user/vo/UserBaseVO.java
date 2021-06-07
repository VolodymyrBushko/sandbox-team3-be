package com.exadel.discountwebapp.user.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Data
public class UserBaseVO implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
