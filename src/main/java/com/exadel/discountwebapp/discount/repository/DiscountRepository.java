package com.exadel.discountwebapp.discount.repository;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.statistics.dto.DiscountViewingDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscountRepository
        extends CrudRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {

    @Modifying
    @Query("update Discount d set d.quantityViews=:quantity where d.id=:discountId")
    void insertDiscountView(@Param("quantity") Long quantity, @Param("discountId") Long discountId);

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.DiscountViewingDTO(d.id, d.title, d.quantityViews)" +
            " FROM Discount d WHERE  d.startDate >= :dataFrom and d.expirationDate < :dataTo ")
    List<DiscountViewingDTO> getDiscountViewing(@Param("dataFrom") LocalDateTime dataFrom, @Param("dataTo") LocalDateTime dataTo);
}
