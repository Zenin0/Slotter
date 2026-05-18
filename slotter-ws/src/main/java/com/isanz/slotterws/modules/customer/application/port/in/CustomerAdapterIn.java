package com.isanz.slotterws.modules.customer.application.port.in;

import com.isanz.slotterws.modules.customer.application.dto.CustomerResponseDTO;
import com.isanz.slotterws.modules.customer.application.port.mapper.CustomerMapper;
import com.isanz.slotterws.modules.customer.domain.Customer;
import com.isanz.slotterws.modules.customer.domain.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerAdapterIn {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerAdapterIn(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO create(Customer customer) {
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public void delete(UUID uuid) {
        customerRepository.deleteById(uuid);
    }

    public void update(Customer entity) {
        customerRepository.save(entity);
    }
}
