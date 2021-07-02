package com.exadel.discountwebapp.location.repository;

import com.exadel.discountwebapp.location.entity.Country;
import com.exadel.discountwebapp.location.entity.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
// TODO: Test!
//    List<Location> findAllByCountryCode(String countryCode);
//    @Query("SELECT loc FROM Location loc " +
//            "LEFT JOIN Country.countryCode cc " +
//            "ON loc.countryCode = cc")
    List<Location> findAllByCountryCode_CountryCode(String countryCode);

    List<Location> findAllByCity(String city);
}
