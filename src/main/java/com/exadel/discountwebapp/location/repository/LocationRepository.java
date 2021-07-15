package com.exadel.discountwebapp.location.repository;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.projections.CityView;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    @Query("SELECT loc.city AS city FROM Location loc " +
            "INNER JOIN loc.country AS lc " +
            "WHERE lc.countryCode = :countryCode " +
            "AND loc.city IS NOT NULL")
    List<CityView> findAllCityView(@Param("countryCode") String countryCode);

    Location findByCountry_CountryCodeAndCityAndAddressLine(String countryCode, String city, String addressLine);
}
