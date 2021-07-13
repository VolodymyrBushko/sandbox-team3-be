package com.exadel.discountwebapp.vendor.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "vendor")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Vendor {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vn_id")
    private Long id;

    @Column(name = "vn_title", length = 50, nullable = false)
    private String title;

    @EqualsAndHashCode.Exclude
    @Column(name = "vn_description", length = 510, nullable = false)
    private String description;

    @EqualsAndHashCode.Exclude
    @Column(name = "vn_image_url", length = 510, nullable = false)
    private String imageUrl;

    @Email
    @Column(name = "vn_email", length = 255, nullable = false, unique = true)
    private String email;

    @EqualsAndHashCode.Exclude
    @CreatedDate
    @Column(name = "vn_created", updatable = false)
    private LocalDateTime created;

    @EqualsAndHashCode.Exclude
    @LastModifiedDate
    @Column(name = "vn_modified")
    private LocalDateTime modified;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name="location_vendor",
            joinColumns = @JoinColumn(name="vn_id"),
            inverseJoinColumns = @JoinColumn(name="loc_id")
    )
    private List<Location> locations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vendor")
    private List<Discount> discounts = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "user_vendor_subscribe",
            joinColumns = @JoinColumn(name = "vn_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_id")
    )
    private List<User> subscribers = new ArrayList<>();
}
