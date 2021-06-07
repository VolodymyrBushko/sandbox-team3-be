package com.exadel.discountwebapp.role.vo;

import com.exadel.discountwebapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class RoleBaseVO implements Serializable {
    protected String name;
    protected List<User> users;
    protected LocalDateTime created;
    protected LocalDateTime modified;
}
