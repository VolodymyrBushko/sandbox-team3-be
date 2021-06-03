package com.exadel.discountwebapp.category.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 25, nullable = false, unique = true)
    private String title;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "image_url", length = 255, nullable = false)
    private String imageUrl;

    @EqualsAndHashCode.Exclude
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Exclude
    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "category")
    private List<Discount> discounts;

    public Category() {
        discounts = new ArrayList<>();
    }

    public Category(String title, String imageUrl) {
        this();
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
