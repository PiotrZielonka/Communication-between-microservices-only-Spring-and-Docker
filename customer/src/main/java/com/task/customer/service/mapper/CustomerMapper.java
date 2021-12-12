package com.task.customer.service.mapper;

import com.task.customer.domain.Customer;
import com.task.customer.service.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {

  CustomerDto toDto(Customer s);

}
