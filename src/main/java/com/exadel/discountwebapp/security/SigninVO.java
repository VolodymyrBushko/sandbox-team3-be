package com.exadel.discountwebapp.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SigninVO {
    private String email;
    private String password;
}
