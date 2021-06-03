package com.exadel.discountwebapp.discount.entity;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discount")
@Data
@EqualsAndHashCode
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 25, nullable = false)
    private String title;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "short_description", length = 50, nullable = false)
    private String shortDescription;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "image_url", length = 255, nullable = false)
    private String imageUrl;

    @Column(name = "percentage", nullable = false)
    private int percentage;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "per_user", nullable = false)
    private int perUser;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @EqualsAndHashCode.Exclude
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

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
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "discount_location",
            joinColumns = @JoinColumn(name = "discount_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private List<Location> locations;

    public Discount() {
        locations = new ArrayList<>();
    }

    public Discount(String title,
                    String shortDescription,
                    String description,
                    String imageUrl,
                    int percentage,
                    int amount,
                    int perUser,
                    BigDecimal price,
                    LocalDateTime startDate,
                    LocalDateTime expirationDate,
                    Category category,
                    Vendor vendor) {
        this();
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.imageUrl = imageUrl;
        this.percentage = percentage;
        this.amount = amount;
        this.perUser = perUser;
        this.price = price;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.category = category;
        this.vendor = vendor;
    }
}
