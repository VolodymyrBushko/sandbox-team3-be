package com.exadel.discountwebapp.vendor.entity;

import com.exadel.discountwebapp.location.entity.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "vendor")
@NoArgsConstructor
@EqualsAndHashCode
public class Vendor {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vn_id")
    private Long id;

    @Column(name = "vn_title", length = 55, nullable = false)
    private String title;

    @EqualsAndHashCode.Exclude
    @Column(name = "vn_description", length = 500, nullable = false)
    private String description;

    @EqualsAndHashCode.Exclude
    @Column(name = "vn_image_url", length = 255, nullable = false)
    private String imageUrl;

    @Column(name = "vn_email", length = 100, nullable = false, unique = true)
    private String email;

    @EqualsAndHashCode.Exclude
    @CreatedDate
    @Column(name = "vn_created", updatable = false)
    private LocalDateTime created;

    @EqualsAndHashCode.Exclude
    @LastModifiedDate
    @Column(name = "vn_modified")
    private LocalDateTime modified;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Vendor(String title, String description, String imageUrl, String email, Location location) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.email = email;
        this.location = location;
    }
}
