package com.exadel.discountwebapp.user.repository;

import com.exadel.discountwebapp.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
