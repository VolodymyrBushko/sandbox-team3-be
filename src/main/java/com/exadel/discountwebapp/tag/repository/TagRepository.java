package com.exadel.discountwebapp.tag.repository;

import com.exadel.discountwebapp.tag.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAllByCategory_Id (Long id);
}