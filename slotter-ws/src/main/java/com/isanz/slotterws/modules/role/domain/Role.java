package com.isanz.slotterws.modules.role.domain;

import com.isanz.slotterws.modules.users.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ColumnDefault("true")
    @Column(nullable = false)
    private Boolean isActive;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "id_role")}, inverseJoinColumns = {@JoinColumn(name = "id_user")})
    private Set<User> users = new LinkedHashSet<>();


}