package com.exadel.discountwebapp.role.repository;

import com.exadel.discountwebapp.role.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
