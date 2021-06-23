package com.exadel.discountwebapp.tag.entity;

import com.exadel.discountwebapp.discount.entity.Discount;
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
@Table(name = "tag_discount")
@EntityListeners(AuditingEntityListener.class)
public class TagDiscount {

    @EmbeddedId
    private TagDiscountId id = new TagDiscountId();

    @ToString.Exclude
    @MapsId("tag_id")
    @JoinColumn(name = "tag_id")
    @ManyToOne
    private Tag tag;

    @ToString.Exclude
    @MapsId("dis_id")
    @JoinColumn(name = "dis_id")
    @ManyToOne
    private Discount discount;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "td_created", updatable = false, nullable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "td_modified", nullable = false)
    private LocalDateTime modified;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagDiscountId implements Serializable {

        @Column(name = "tag_id")
        private Long tagId;

        @Column(name = "dis_id")
        private Long discountId;
    }
}