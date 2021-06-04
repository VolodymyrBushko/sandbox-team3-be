package com.exadel.discountwebapp.user.entity;

import com.exadel.discountwebapp.role.entity.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode
@Table(name="user")
public class User {

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", updatable = false, nullable = false)
//    private UUID id;

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;


    @Column(name = "usr_name", length = 50, nullable = false)
    private String name;

    @Column(name = "usr_surname", length = 50, nullable = false)
    private String surname;

    @Column(name="usr_email", length = 100, unique = true, nullable = false)
    private String email;

    @EqualsAndHashCode.Exclude
    @Column(name="usr_password", length = 255, nullable = false)
    private String password;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "role_id")
    private Role role;

//    @ManyToOne
//    @EqualsAndHashCode.Exclude
//    @JoinColumn(name = "loc_id")
//    private Location location;


//    @ManyToMany(mappedBy = "discount")
////    @JoinTable
//    private List<Discount> discounts;


    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name="usr_created", nullable = false, updatable = false)
    private LocalDateTime  created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name="usr_modified", nullable = false)
    private LocalDateTime modified;

}
