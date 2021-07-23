package com.exadel.discountwebapp.vendor.repository;

import com.exadel.discountwebapp.vendor.entity.Vendor;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@JaversSpringDataAuditable
@Repository
public interface VendorRepository
        extends CrudRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {

    Optional<Vendor> findByTitle(String title);

    Optional<Vendor> findByEmail(String email);

    Optional<Vendor> findByPhoneNumber(String email);

    @Query("select u.email from User u left join u.subscriptions s where s.id = ?1")
    List<String> findAllSubEmailsByVendorId(Long id);
}
