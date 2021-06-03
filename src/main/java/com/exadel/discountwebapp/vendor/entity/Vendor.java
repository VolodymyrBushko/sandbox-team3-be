package com.exadel.discountwebapp.vendor.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendor")
@Data
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "vendor")
    private List<Discount> discounts;

    public Vendor() {
        discounts = new ArrayList<>();
    }
}
