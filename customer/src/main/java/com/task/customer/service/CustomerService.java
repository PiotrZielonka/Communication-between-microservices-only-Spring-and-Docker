package com.task.customer.service;

import com.task.customer.service.dto.CustomerDto;
import java.util.Optional;

public interface CustomerService {

  Optional<CustomerDto> findByCreditId(Long id);

  void save(CustomerDto customerDto);
}
