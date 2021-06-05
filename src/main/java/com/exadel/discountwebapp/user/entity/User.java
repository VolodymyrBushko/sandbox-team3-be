package com.exadel.discountwebapp.user.entity;

import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.userdiscount.entitiy.UserDiscount;
import com.exadel.discountwebapp.location.entity.Location;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "usr_name", length = 50, nullable = false)
    private String name;

    @Column(name = "usr_surname", length = 50, nullable = false)
    private String surname;

    @Column(name = "usr_email", length = 255, unique = true, nullable = false)
    private String email;

    @EqualsAndHashCode.Exclude
    @Column(name = "usr_password", length = 510, nullable = false)
    private String password;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "loc_id")
    private Location location;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "rol_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private List<UserDiscount> discounts = new ArrayList<>();

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "usr_created", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "usr_modified", nullable = false)
    private LocalDateTime modified;

}
