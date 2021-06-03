package com.exadel.discountwebapp.vendor.entity;

import com.exadel.discountwebapp.location.entity.Location;
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
@Table(name = "vendor")
@NoArgsConstructor
@EqualsAndHashCode
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 55, nullable = false)
    private String title;

    @EqualsAndHashCode.Exclude
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @EqualsAndHashCode.Exclude
    @Column(name = "image_url", length = 255, nullable = false)
    private String imageUrl;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @EqualsAndHashCode.Exclude
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Exclude
    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
