package com.isanz.slotterws.modules.customer.application.port.out;

import com.isanz.slotterws.modules.customer.application.dto.CustomerFullDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerResponseDTO;
import com.isanz.slotterws.modules.customer.application.port.mapper.CustomerMapper;
import com.isanz.slotterws.modules.customer.domain.Customer;
import com.isanz.slotterws.modules.customer.domain.CustomerRepository;
import com.isanz.slotterws.shared.exceptions.alreadyexists.CustomerAlreadyExistsException;
import com.isanz.slotterws.shared.exceptions.notfound.CustomerNotFoundException;
import com.isanz.slotterws.shared.implementations.adapter.out.AdapterOut;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerAdapterOut implements AdapterOut<CustomerResponseDTO, Customer, CustomerFullDTO> {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerAdapterOut(CustomerRepository customerRepository, @Lazy CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerFullDTO> findAllFull() {
        return List.of();
    }

    @Override
    public List<CustomerResponseDTO> findAll() {
        return customerMapper.toDTOs(customerRepository.findAll());
    }

    @Override
    public Customer findOne(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }

        return customer.get();
    }

    @Override
    public boolean alreadyExists(String parameter) {
        Optional<Customer> customer = customerRepository.findByEmail(parameter);

        if (customer.isPresent()) {
            throw new CustomerAlreadyExistsException(parameter);
        }

        customer = customerRepository.findByPhone(parameter);

        if (customer.isPresent()) {
            throw new CustomerAlreadyExistsException(parameter);
        }

        return false;

    }

    @Override
    public CustomerFullDTO show(UUID uuid) {
        return null;
    }

    public List<CustomerResponseDTO> findAllBySlug(String slug) {
        return customerMapper.toDTOs(customerRepository.findByCompany_Slug(slug));
    }
}
