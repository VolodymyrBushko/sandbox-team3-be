package com.exadel.discountwebapp.location.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "location")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Location {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_id")
    private Long id;

    @Column(name = "loc_city", length = 50, nullable = true)
    private String city;

    @ManyToOne
    @JoinColumn(name = "locations")
    private Country countryCode;

    @Column(name = "loc_address_line", length = 255, nullable = true)
    private String addressLine;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "loc_created", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "loc_modified", nullable = false)
    private LocalDateTime modified;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "location")
    private List<Vendor> vendors = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "locations")
    private List<Discount> discounts = new ArrayList<>();
}
