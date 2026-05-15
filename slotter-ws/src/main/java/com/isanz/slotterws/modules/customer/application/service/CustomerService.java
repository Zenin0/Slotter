package com.isanz.slotterws.modules.customer.application.service;

import com.isanz.slotterws.modules.customer.application.dto.CustomerRequestDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerResponseDTO;
import com.isanz.slotterws.modules.customer.application.port.in.CustomerAdapterIn;
import com.isanz.slotterws.modules.customer.application.port.mapper.CustomerMapper;
import com.isanz.slotterws.modules.customer.application.port.out.CustomerAdapterOut;
import com.isanz.slotterws.modules.customer.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerAdapterOut customerAdapterOut;
    private final CustomerAdapterIn customerAdapterIn;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerAdapterOut customerAdapterOut, CustomerAdapterIn customerAdapterIn, CustomerMapper customerMapper) {
        this.customerAdapterOut = customerAdapterOut;
        this.customerAdapterIn = customerAdapterIn;
        this.customerMapper = customerMapper;
    }

    public List<CustomerResponseDTO> getAll() {
        return customerAdapterOut.findAll();
    }

    public CustomerResponseDTO create(CustomerRequestDTO requestDTO) {

        customerAdapterOut.alreadyExists(requestDTO.getEmail());
        customerAdapterOut.alreadyExists(requestDTO.getPhone());

        return customerAdapterIn.create(requestDTO);
    }

    public List<CustomerResponseDTO> getAllBySlug(String slug) {
        return customerAdapterOut.findAllBySlug(slug);
    }

    public void delete(UUID uuid) {
        customerAdapterIn.delete(uuid);
    }

    public void update(CustomerRequestDTO request, UUID uuid) {

        customerAdapterOut.alreadyExists(request.getEmail());
        customerAdapterOut.alreadyExists(request.getPhone());

        Customer customer = customerMapper.toEntity(request, uuid);
        customerAdapterIn.update(customer);
    }
}
