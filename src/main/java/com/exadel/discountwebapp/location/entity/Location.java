package com.exadel.discountwebapp.location.entity;

import com.exadel.discountwebapp.vendor.entity.Vendor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", length = 50, unique = true, nullable = false)
    private String country;

    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @EqualsAndHashCode.Exclude
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "location")
    private List<Vendor> vendors;


    public Location(String country, String city, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.country = country;
        this.city = city;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
