package com.exadel.discountwebapp.location.repository;

import com.exadel.discountwebapp.location.entity.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query(value =
            "select location.loc_id, " +
                    "location.country_code, " +
                    "location.loc_city, " +
                    "location.loc_address_line," +
                    "location.loc_created, " +
                    "location.loc_modified " +
                    "from location " +
                    "left join country " +
                    "on location.country_code = country.country_code " +
                    "where country.country_code = :countryCode", nativeQuery = true)
    List<Location> findAllByCountryCode(@Param("countryCode") String countryCode);

    List<Location> findAllByCity(String city);
}
