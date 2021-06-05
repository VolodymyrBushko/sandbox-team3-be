package com.exadel.discountwebapp.role.entity;

import com.exadel.discountwebapp.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Builder
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "rol_id")
    private Long id;

    @Column(name = "rol_name", length = 25, unique = true, nullable = false)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "role")
    @EqualsAndHashCode.Exclude
    private List<User> users = new ArrayList<>();

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "rol_created", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name = "rol_modified", nullable = false)
    private LocalDateTime modified;
}