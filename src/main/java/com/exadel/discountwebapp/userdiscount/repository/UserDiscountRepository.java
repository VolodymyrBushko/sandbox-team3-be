package com.exadel.discountwebapp.userdiscount.repository;

import com.exadel.discountwebapp.statistics.dto.CategoryDTO;
import com.exadel.discountwebapp.statistics.dto.UserDTO;

import com.exadel.discountwebapp.statistics.dto.VendorDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedCategoryDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedUserDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedVendorDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedUsersPreferenceDTO;
import com.exadel.discountwebapp.userdiscount.entity.UserDiscount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserDiscountRepository extends CrudRepository<UserDiscount, UserDiscount.UserDiscountId> {

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.UserDTO(ud.user.id, ud.user.firstName, ud.user.lastName, ud.user.email, count (ud.user))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dateFrom and ud.created <= :dateTo group by ud.user.id, ud.user.firstName, ud.user.lastName, ud.user.email")
    List<UserDTO> getUserDiscountStatistics(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.CategoryDTO(ud.discount.category.id, ud.discount.category.title, count (ud.discount))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dateFrom and ud.created <= :dateTo group by ud.discount.category.id, ud.discount.category.title")
    List<CategoryDTO> getCategoryDiscountStatistics(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.VendorDTO(ud.discount.vendor.id, ud.discount.vendor.title, count (ud.discount))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dateFrom and ud.created <= :dateTo group by ud.discount.vendor.id, ud.discount.vendor.title")
    List<VendorDTO> getVendorDiscountStatistics(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedUserDTO(ud.user.firstName, ud.user.lastName, ud.user.email, ud.user.location.country.countryFullName, ud.user.location.city, ud.user.role.name, count (ud.user))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dateFrom and ud.created <= :dateTo group by ud.user.firstName, ud.user.lastName, ud.user.email, ud.user.location.country.countryFullName, ud.user.location.city, ud.user.role.name")
    List<ExtendedUserDTO> getExtendedUserDiscountStatistics(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedCategoryDTO(ud.discount.category.title, count (ud.discount))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dateFrom and ud.created <= :dateTo group by ud.discount.category.title")
    List<ExtendedCategoryDTO> getExtendedCategoryDiscountStatistics(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedVendorDTO(ud.discount.vendor.title, ud.discount.vendor.description, ud.discount.vendor.email, ud.discount.vendor.phoneNumber, count (ud.discount))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dateFrom and ud.created <= :dateTo group by ud.discount.vendor.title, ud.discount.vendor.description, ud.discount.vendor.email, ud.discount.vendor.phoneNumber")
    List<ExtendedVendorDTO> getExtendedVendorDiscountStatistics(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedUsersPreferenceDTO(ud.user.firstName, ud.user.lastName, ud.user.email, ud.user.location.country.countryFullName, ud.user.location.city, ud.user.role.name, ud.discount.category.title, count (ud.user))" +
            " FROM UserDiscount ud WHERE  ud.created >= :dateFrom and ud.created <= :dateTo group by ud.user.firstName, ud.user.lastName, ud.user.email, ud.user.location.country.countryFullName, ud.user.location.city, ud.user.role.name, ud.discount.category.title")
    List<ExtendedUsersPreferenceDTO> getExtendedUsersPreference(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
}
