package com.exadel.discountwebapp.vendor.entity;

import com.exadel.discountwebapp.location.entity.Location;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "loc_id")
    private Location location;
}
