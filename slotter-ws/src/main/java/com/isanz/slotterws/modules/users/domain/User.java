package com.isanz.slotterws.modules.users.domain;

import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.role.domain.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.jspecify.annotations.NonNull;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profileImage;

    @ColumnDefault("true")
    @Column(nullable = false)
    private Boolean isActive;
    
    @NonNull
    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new LinkedHashSet<>();

}
