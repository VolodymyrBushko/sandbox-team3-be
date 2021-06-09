package com.exadel.discountwebapp.user.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserBaseVO implements Serializable {

    @NotNull(message = "Name cannot be null")
    private String firstName;

    @NotNull(message = "Name cannot be null")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;
}
