package com.exadel.discountwebapp.role.entity;

import com.exadel.discountwebapp.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "roles")
public class Role {

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", updatable = false, nullable = false)
//    private UUID id;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 25, unique = true, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="modified_at", nullable = false)
    private Date modifiedAt;

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//    private List<User> users = new ArrayList<>();

}
