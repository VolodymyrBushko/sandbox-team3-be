package com.exadel.discountwebapp.category.repository;

import com.exadel.discountwebapp.category.entity.Category;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@JaversSpringDataAuditable
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByTitle(String title);

    boolean existsByTitle(String title);
}
