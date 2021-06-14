package com.exadel.discountwebapp.user.repository;

import com.exadel.discountwebapp.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
