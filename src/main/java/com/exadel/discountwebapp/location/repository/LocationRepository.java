package com.exadel.discountwebapp.location.repository;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.projections.CityView;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    List<CityView> findAllByCountry_CountryCode(String countryCode);

    Location findByCountry_CountryCodeAndCityAndAddressLine(String countryCode, String city, String addressLine);
}
