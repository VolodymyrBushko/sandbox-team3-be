package com.exadel.discountwebapp.userdiscount.entities;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Builder
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
//@Table(name = "user_discount")
//public class UserDiscount {
//
//    @ToString.Exclude
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "user_id", nullable = true)
//    private User user;
//
//    @ToString.Exclude
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "discount_id", nullable = true)
//    private Discount discount;
//
//    @Column(name = "discount_amount")
//    int discountAmount;
//
//    @Column(name = "created_at")
//    LocalDateTime createdAt;
//
//    @Column(name = "modified_at")
//    LocalDateTime modifiedAt;
//}
