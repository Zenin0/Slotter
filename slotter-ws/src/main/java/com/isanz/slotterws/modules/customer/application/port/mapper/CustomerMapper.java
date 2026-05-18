package com.isanz.slotterws.modules.customer.application.port.mapper;

import com.isanz.slotterws.modules.customer.application.dto.CustomerFullDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerRequestDTO;
import com.isanz.slotterws.modules.customer.application.dto.CustomerResponseDTO;
import com.isanz.slotterws.modules.customer.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponseDTO toDTO(Customer entity);

    List<CustomerResponseDTO> toDTOs(List<Customer> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    Customer fromDTO(CustomerRequestDTO request);

    CustomerFullDTO toFullDTO(Customer entity);

    List<CustomerFullDTO> toFullDTOs(List<Customer> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateEntity(CustomerRequestDTO request, @MappingTarget Customer customer);
}