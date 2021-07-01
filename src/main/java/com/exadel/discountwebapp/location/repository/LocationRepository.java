package com.exadel.discountwebapp.location.repository;

import com.exadel.discountwebapp.location.entity.Country;
import com.exadel.discountwebapp.location.entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findAllByCountry(String country);

    List<Location> findAllByCity(String city);
}
