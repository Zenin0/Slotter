package com.isanz.slotterws.modules.company.domain;

import com.isanz.slotterws.modules.users.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "company")
@ToString(exclude = {"users"})
public class Company {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "company_logo")
    private String companyLogo;

    @Column(nullable = false)
    private String slug;

    @OneToMany
    @JoinColumn(name = "company_id")
    private Set<User> users = new LinkedHashSet<>();


}