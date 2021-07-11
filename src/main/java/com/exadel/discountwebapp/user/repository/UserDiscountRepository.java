package com.exadel.discountwebapp.user.repository;

import com.exadel.discountwebapp.statistics.dto.UserDiscountCountDTO;
import com.exadel.discountwebapp.user.entity.UserDiscount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDiscountRepository extends CrudRepository<UserDiscount, UserDiscount.UserDiscountId> {

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.UserDTO(ud.user.firstName, ud.user.lastName, ud.user.email) FROM UserDiscount ud")
    List<UserDiscountCountDTO> getUserDiscountStatistics();
}
