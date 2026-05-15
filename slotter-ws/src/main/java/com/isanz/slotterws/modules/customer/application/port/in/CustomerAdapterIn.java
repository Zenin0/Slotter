package com.isanz.slotterws.modules.customer.application.port.in;

import com.isanz.slotterws.modules.customer.application.dto.CustomerRequestDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerResponseDTO;
import com.isanz.slotterws.modules.customer.application.port.mapper.CustomerMapper;
import com.isanz.slotterws.modules.customer.domain.Customer;
import com.isanz.slotterws.modules.customer.domain.CustomerRepository;
import com.isanz.slotterws.shared.implementations.adapter.in.AdapterIn;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerAdapterIn implements AdapterIn<CustomerRequestDTO, CustomerResponseDTO, Customer> {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerAdapterIn(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDTO create(CustomerRequestDTO request) {
        Customer customer = customerMapper.fromDTO(request);

        customer = customerRepository.save(customer);

        return customerMapper.toDTO(customer);
    }

    @Override
    public void delete(UUID uuid) {
        customerRepository.deleteById(uuid);
    }

    @Override
    public void update(Customer entity) {
        customerRepository.save(entity);
    }
}
