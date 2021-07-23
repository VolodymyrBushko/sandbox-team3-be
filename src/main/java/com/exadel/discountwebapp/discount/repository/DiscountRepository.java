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

    @Query(value = "select dis.dis_title as title, dis.dis_short_description as shortDescription, " +
            "dis.dis_description as description, dis.dis_promocode as promocode, dis.dis_percentage as percentage," +
            " dis.dis_flat_amount as flatAmount,\n" +
            "dis.dis_created as created, " +
            "dis.dis_start_date as startDate, " +
            "dis.dis_expiration_date as expirationDate," +
            " vn.vn_title as vendorTitle, " +
            "cat.cat_title as categoryTitle," +
            " dis.dis_viewed as viewNumber, dis_activated as activated\n" +
            "from discount dis \n" +
            "inner join vendor vn\n" +
            "on vn.vn_id= dis.vn_id\n" +
            "inner join category cat\n" +
            "on cat.cat_id=dis.cat_id\n" +
            "inner join \n" +
            "(select ud.dis_id as dis_id, count(1) as dis_activated\n" +
            "\tfrom discount d\n" +
            "\tinner join user_discount ud on d.dis_id=ud.dis_id\n" +
            "\tgroup by ud.dis_id) as grouped_discounts\n" +
            "\ton grouped_discounts.dis_id=dis.dis_id", nativeQuery = true)
    List<ExtendedDiscountViews> getExtendedDiscountSummary();
}
