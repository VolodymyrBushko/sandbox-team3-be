package com.exadel.discountwebapp.role.entity;

import com.exadel.discountwebapp.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@EqualsAndHashCode
@Table(name = "role")
public class Role {

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", updatable = false, nullable = false)
//    private UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "rol_id")
    private Long id;

    @Column(name = "rol_name", length = 25, unique = true, nullable = false)
    private String name;

     @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();

    @CreatedDate
    @EqualsAndHashCode.Exclude
    @Column(name="rol_created", nullable = false, updatable = false)
    private LocalDateTime  created;

    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    @Column(name="rol_modified", nullable = false)
    private LocalDateTime modified;
}
