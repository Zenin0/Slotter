package com.isanz.slotterws.modules.customer.application.port.mapper;

import com.isanz.slotterws.modules.company.domain.Company;
import com.isanz.slotterws.modules.customer.application.dto.CustomerFullDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerRequestDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerResponseDTO;
import com.isanz.slotterws.modules.customer.application.port.out.CustomerAdapterOut;
import com.isanz.slotterws.modules.customer.domain.Customer;
import com.isanz.slotterws.modules.customer.domain.CustomerRepository;
import com.isanz.slotterws.shared.exceptions.notfound.CustomerNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.mapper.Mapper;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerRequestDTO, CustomerResponseDTO, CustomerFullDTO> {

    @Autowired
    private final EntityManager entityManager;
    @Autowired
    private final CustomerAdapterOut customerAdapterOut;

    public CustomerMapper(EntityManager entityManager, @Lazy CustomerAdapterOut customerAdapterOut) {
        this.entityManager = entityManager;
        this.customerAdapterOut = customerAdapterOut;
    }

    @Override
    public CustomerResponseDTO toDTO(Customer entity) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());

        return dto;

    }

    @Override
    public List<CustomerResponseDTO> toDTOs(List<Customer> entities) {
        List<CustomerResponseDTO> dtos = new ArrayList<>();

        for (Customer customer : entities) {
            dtos.add(toDTO(customer));
        }

        return dtos;
    }

    @Override
    public Customer fromDTO(CustomerRequestDTO request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setCompany(entityManager.getReference(Company.class, request.getCompany()));

        return customer;

    }

    @Override
    public CustomerFullDTO toFullDTO(Customer entity) {
        return null;
    }

    @Override
    public List<CustomerFullDTO> toFullDTOs(List<Customer> entities) {
        return List.of();
    }

    @Override
    public Customer toEntity(CustomerRequestDTO request, UUID uuid) {
        Customer customer = customerAdapterOut.findOne(uuid);

        customer.setEmail(request.getEmail());
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        return customer;
    }
}
