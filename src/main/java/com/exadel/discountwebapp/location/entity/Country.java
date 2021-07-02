package com.exadel.discountwebapp.location.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "country")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Country {
    @Id
    @Column(name = "country_code", length = 50, nullable = false, unique = true)
    String countryCode;

    @Column(name = "country_full_name", length = 255, nullable = false, unique = true)
    String countryFullName;

    @OneToMany(mappedBy = "countryCode")
    private List<Location> locations;
}
