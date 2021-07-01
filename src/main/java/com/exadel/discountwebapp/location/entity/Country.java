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
    @Column(name = "country_short_name", length = 50, nullable = false, unique = true)
    String countryShortName;

    @Column(name = "country_full_name", length = 255, nullable = false, unique = true)
    String countryFullName;

//    @ManyToOne
//    @JoinColumn(name = "loc_id")
//    private Location location;

    @OneToMany(mappedBy = "country")
    private List<Location> locations;
}
