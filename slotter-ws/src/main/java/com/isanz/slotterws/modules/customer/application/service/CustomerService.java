package com.isanz.slotterws.modules.customer.application.service;

import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.customer.application.dto.CustomerRequestDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerResponseDTO;
import com.isanz.slotterws.modules.customer.application.port.in.CustomerAdapterIn;
import com.isanz.slotterws.modules.customer.application.port.mapper.CustomerMapper;
import com.isanz.slotterws.modules.customer.application.port.out.CustomerAdapterOut;
import com.isanz.slotterws.modules.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerAdapterOut customerAdapterOut;
    private final CustomerAdapterIn customerAdapterIn;
    private final CustomerMapper customerMapper;
    private final EntityManager entityManager;

    public CustomerService(CustomerAdapterOut customerAdapterOut, CustomerAdapterIn customerAdapterIn,
                           CustomerMapper customerMapper, EntityManager entityManager) {
        this.customerAdapterOut = customerAdapterOut;
        this.customerAdapterIn = customerAdapterIn;
        this.customerMapper = customerMapper;
        this.entityManager = entityManager;
    }

    public List<CustomerResponseDTO> getAll() {
        return customerAdapterOut.findAll();
    }

    public CustomerResponseDTO create(CustomerRequestDTO request) {
        customerAdapterOut.alreadyExists(request.getEmail());
        customerAdapterOut.alreadyExists(request.getPhone());
        Customer customer = customerMapper.fromDTO(request);
        customer.setCompany(entityManager.getReference(Company.class, request.getCompany()));
        return customerAdapterIn.create(customer);
    }

    public List<CustomerResponseDTO> getAllBySlug(String slug) {
        return customerAdapterOut.findAllBySlug(slug);
    }

    public void delete(UUID uuid) {
        customerAdapterIn.delete(uuid);
    }

    public void update(UUID uuid, CustomerRequestDTO request) {
        customerAdapterOut.alreadyExists(request.getEmail());
        customerAdapterOut.alreadyExists(request.getPhone());
        Customer existing = customerAdapterOut.findOne(uuid);
        customerMapper.updateEntity(request, existing);
        customerAdapterIn.update(existing);
    }
}