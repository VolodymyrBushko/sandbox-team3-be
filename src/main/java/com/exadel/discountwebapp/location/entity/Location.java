package com.exadel.discountwebapp.location.entity;

import com.exadel.discountwebapp.vendor.entity.Vendor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "location")
public class Location {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_id")
    private Long id;

    @Column(name = "loc_country", length = 50, nullable = false)
    private String country;

    @Column(name = "loc_city", length = 50, nullable = false)
    private String city;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "loc_created", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "loc_modified", nullable = false)
    private LocalDateTime modified;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "location")
    private List<Vendor> vendors;


    public Location(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
