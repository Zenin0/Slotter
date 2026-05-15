package com.isanz.slotterws.modules.customer.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByCompany_Slug(String companySlug);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhone(String phone);
}
