package com.exadel.discountwebapp.discount.repository;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.statistics.dto.DiscountViewingDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedDiscountViewsDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository
        extends CrudRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.DiscountViewingDTO(d.id, d.title, d.viewNumber)" +
            " FROM Discount d")
    List<DiscountViewingDTO> getDiscountSummary();

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedDiscountViewsDTO(d.title, d.shortDescription, d.description, d.promocode, d.percentage, d.flatAmount, d.startDate, d.expirationDate, d.vendor.title, d.category.title, d.viewNumber)" +
            " FROM Discount d")
    List<ExtendedDiscountViewsDTO> getExtendedDiscountSummary();
}
