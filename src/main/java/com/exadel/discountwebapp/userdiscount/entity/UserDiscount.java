package com.exadel.discountwebapp.userdiscount.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "user_discount")
@EntityListeners(AuditingEntityListener.class)
public class UserDiscount {

    @ToString.Exclude
    @ManyToOne(optional = true)
    @JoinColumn(name = "ud_usr_id", nullable = true)
    private User user;

    @ToString.Exclude
    @ManyToOne(optional = true)
    @JoinColumn(name = "ud_dis_id ", nullable = true)
    private Discount discount;

    @Column(name = "ud_discount_quantity", nullable = false)
    private int discountQuantity;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "ud_created", updatable = false, nullable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "ud_modified", nullable = false)
    private LocalDateTime modified;
}
