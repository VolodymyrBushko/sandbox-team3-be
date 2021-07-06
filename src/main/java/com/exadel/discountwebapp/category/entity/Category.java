package com.exadel.discountwebapp.category.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.tag.entity.Tag;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "cat_id")
    private Long id;

    @Column(name = "cat_title", length = 25, nullable = false, unique = true)
    private String title;

    @EqualsAndHashCode.Exclude
    @Column(name = "cat_image_url", length = 510)
    private String imageUrl;

    @EqualsAndHashCode.Exclude
    @CreatedDate
    @Column(name = "cat_created", nullable = false, updatable = false)
    private LocalDateTime created;

    @EqualsAndHashCode.Exclude
    @LastModifiedDate
    @Column(name = "cat_modified", nullable = false)
    private LocalDateTime modified;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "category")
    private List<Discount> discounts = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();
}
