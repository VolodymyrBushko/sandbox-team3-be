package com.exadel.discountwebapp.location.repository;

import com.exadel.discountwebapp.location.entity.Location;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    List<Location> findAllByCountry_CountryCode(String countryCode);

    List<Location> findAllByCity(String city);

    List<Location> findAllByIdIn(Collection<Long> ids);
}
