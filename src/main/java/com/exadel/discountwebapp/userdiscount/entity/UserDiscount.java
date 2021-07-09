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
    @MapsId("usr_id")
    @JoinColumn(name = "usr_id")
    @ManyToOne
    private User user;

    @ToString.Exclude
    @MapsId("dis_id")
    @JoinColumn(name = "dis_id")
    @ManyToOne
    private Discount discount;

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

        @Column(name = "usr_id")
        private Long userId;

        @Column(name = "dis_id")
        private Long discountId;
    }
}
