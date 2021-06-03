package com.exadel.discountwebapp.location.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @EqualsAndHashCode.Exclude
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Exclude
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    public Location(String country, String city, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.country = country;
        this.city = city;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
