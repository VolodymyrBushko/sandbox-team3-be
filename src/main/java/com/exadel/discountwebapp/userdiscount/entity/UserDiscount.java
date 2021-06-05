package com.exadel.discountwebapp.userdiscount.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
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

    @EmbeddedId
    private UserDiscountId id = new UserDiscountId();

    @ToString.Exclude
    @MapsId("userId")
    @ManyToOne
    private User user;

    @ToString.Exclude
    @MapsId("discountId")
    @ManyToOne
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

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDiscountId implements Serializable {

        private Long userId;

        private Long discountId;
    }
}
