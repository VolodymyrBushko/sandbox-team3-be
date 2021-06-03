package com.exadel.discountwebapp.user.entity;

import com.exadel.discountwebapp.role.entity.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode
@Table(name="users")
public class User {

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", updatable = false, nullable = false)
//    private UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    @Column(name="email", length = 100, unique = true, nullable = false)
    private String email;


    @Column(name="password", length = 255, nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="modified_at", nullable = false)
    private Date modifiedAt;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_id")
//    private Role role;

}
