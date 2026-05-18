package com.isanz.slotterws.modules.company.domain;

import com.isanz.slotterws.modules.bookings.domain.Booking;
import com.isanz.slotterws.modules.customer.domain.Customer;
import com.isanz.slotterws.modules.users.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Audited
@Table(name = "company")
@ToString(exclude = {"users"})
public class Company {

    @OneToMany(mappedBy = "company")
    private List<User> users;

    @NonNull
    @OneToMany(mappedBy = "company")
    private Set<Customer> customers = new LinkedHashSet<>();

    @NonNull
    @OneToMany(mappedBy = "company")
    private Set<Booking> bookings = new LinkedHashSet<>();

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "company_logo")
    private String companyLogo;

    @Column(name = "name", nullable = false)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;


}