package com.exadel.discountwebapp.location.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "country")
@EntityListeners(AuditingEntityListener.class)
public class Country {
    @EqualsAndHashCode.Exclude
    @Id
    @Column(name = "country_code", length = 50, nullable = false, unique = true)
    String countryCode;

    @Column(name = "country_full_name", length = 255, nullable = false, unique = true)
    String countryFullName;

    @ToStringExclude
    @OneToMany(mappedBy = "countryCode")
    private List<Location> locations;
}
