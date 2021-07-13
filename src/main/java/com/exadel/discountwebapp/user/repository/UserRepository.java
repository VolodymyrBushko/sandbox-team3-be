package com.exadel.discountwebapp.user.repository;

import com.exadel.discountwebapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findUserByEmail(String email);

    @Query("select v.id from Vendor v left join User u on v.id in (select s.id from u.subscriptions s) where u.email = ?1")
    List<Long> findSubscribersIdsByEmail(String email);
}
