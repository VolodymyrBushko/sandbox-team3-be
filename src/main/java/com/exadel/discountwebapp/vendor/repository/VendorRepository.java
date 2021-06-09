package com.exadel.discountwebapp.vendor.repository;

import com.exadel.discountwebapp.vendor.entity.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {
    Optional<Vendor> findByTitle(String title);
}
