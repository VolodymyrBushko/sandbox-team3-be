package com.exadel.discountwebapp.user.repository;

import com.exadel.discountwebapp.statistics.dto.CategoryDTO;
import com.exadel.discountwebapp.statistics.dto.UserDTO;

import com.exadel.discountwebapp.statistics.dto.VendorDTO;
import com.exadel.discountwebapp.user.entity.UserDiscount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserDiscountRepository extends CrudRepository<UserDiscount, UserDiscount.UserDiscountId> {

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.UserDTO(ud.user.firstName, ud.user.lastName, ud.user.email, count (ud.user))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dataFrom and ud.created < :dataTo group by ud.user.firstName, ud.user.lastName, ud.user.email")
    List<UserDTO> getUserDiscountStatistics(@Param("dataFrom") LocalDateTime dataFrom, @Param("dataTo") LocalDateTime dataTo);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.CategoryDTO(ud.discount.category.title, count (ud.discount))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dataFrom and ud.created < :dataTo group by ud.discount.category.title")
    List<CategoryDTO> getCategoryDiscountStatistics(@Param("dataFrom") LocalDateTime dataFrom, @Param("dataTo") LocalDateTime dataTo);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.VendorDTO(ud.discount.vendor.title, count (ud.discount))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dataFrom and ud.created < :dataTo group by ud.discount.vendor.title")
    List<VendorDTO> getVendorDiscountStatistics(@Param("dataFrom") LocalDateTime dataFrom, @Param("dataTo") LocalDateTime dataTo);
}
