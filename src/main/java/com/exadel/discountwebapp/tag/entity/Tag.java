package com.exadel.discountwebapp.tag.entity;

import com.exadel.discountwebapp.category.entity.Category;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "tag")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_name", length = 50, unique = true, nullable = false)
    private String name;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "tag_created", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "tag_modified", nullable = false)
    private LocalDateTime modified;
}