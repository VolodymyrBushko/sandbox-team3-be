package com.exadel.discountwebapp.discount.repository;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.statistics.dto.DiscountViewingDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedDiscountViews;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@JaversSpringDataAuditable
@Repository
public interface DiscountRepository
        extends CrudRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {

    @Query(value = "SELECT new com.exadel.discountwebapp.statistics.dto.DiscountViewingDTO(d.id, d.title, d.viewNumber)" +
            " FROM Discount d")
    List<DiscountViewingDTO> getDiscountSummary();

        @Query(value = "select dis.dis_title as title, " +
                              "dis.dis_short_description as shortDescription, " +
                              "dis.dis_description as description, " +
                              "dis.dis_promocode as promocode, " +
                              "dis.dis_percentage as percentage, " +
                              "dis.dis_flat_amount as flatAmount, " +
                              "dis.dis_created as created, " +
                              "dis.dis_start_date as startDate, " +
                              "dis.dis_expiration_date as expirationDate, " +
                              "vn.vn_title as vendorTitle, " +
                              "cat.cat_title as categoryTitle, " +
                              "dis.dis_viewed as viewNumber, " +
                              "(select count(1) " +
                              "from discount d  " +
                              "inner join user_discount ud on " +
                              "d.dis_id=ud.dis_id " +
                              "where d.dis_id=dis.dis_id " +
                              "group by ud.dis_id) as activated " +
                "from discount dis " +
                "inner join vendor vn " +
                "on vn.vn_id= dis.vn_id " +
                "inner join category cat " +
                "on cat.cat_id=dis.cat_id ", nativeQuery = true)
    List<ExtendedDiscountViews> getExtendedDiscountSummary();
}
