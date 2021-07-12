package com.exadel.discountwebapp.user.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.userdiscount.entity.UserDiscount;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "`user`")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @NotNull
    @Column(name = "usr_first_name", length = 50)
    private String firstName;

    @NotNull
    @Column(name = "usr_last_name", length = 50)
    private String lastName;

    @NotNull
    @Column(name = "usr_email", length = 255, unique = true)
    private String email;

    @NotNull
    @Column(name = "usr_password", length = 510)
    private String password;

    @EqualsAndHashCode.Exclude
    @Column(name = "usr_image_url", length = 510, nullable = false)
    private String imageUrl;

    @NotNull
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "loc_id")
    private Location location;

    @NotNull
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "rol_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private List<UserDiscount> discounts = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "favoriteUsers")
    private List<Discount> favoriteDiscounts = new ArrayList<>();

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "usr_created", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "usr_modified", nullable = false)
    private LocalDateTime modified;
}