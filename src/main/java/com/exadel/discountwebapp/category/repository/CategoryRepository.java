package com.exadel.discountwebapp.category.repository;

import com.exadel.discountwebapp.category.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
