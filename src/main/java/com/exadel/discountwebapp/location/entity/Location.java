package com.exadel.discountwebapp.location.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "locations")
    private List<Discount> discounts;

    public Location() {
        discounts = new ArrayList<>();
    }
}
