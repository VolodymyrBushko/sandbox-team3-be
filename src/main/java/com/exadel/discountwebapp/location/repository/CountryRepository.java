package com.exadel.discountwebapp.location.repository;

import com.exadel.discountwebapp.location.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCountryCode(String countryCode);
}
