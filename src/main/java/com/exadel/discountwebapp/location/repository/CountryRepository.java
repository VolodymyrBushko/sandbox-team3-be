package com.exadel.discountwebapp.location.repository;

import com.exadel.discountwebapp.location.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
