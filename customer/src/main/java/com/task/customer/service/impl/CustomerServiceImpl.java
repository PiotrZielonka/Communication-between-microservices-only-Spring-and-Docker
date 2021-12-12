package com.task.customer.service.impl;

import com.task.customer.domain.Customer;
import com.task.customer.repository.CustomerRepository;
import com.task.customer.service.CustomerService;
import com.task.customer.service.dto.CustomerDto;
import com.task.customer.service.mapper.CustomerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

  private final CustomerRepository customerRepository;

  private final CustomerMapper customerMapper;


  public CustomerServiceImpl(
      CustomerRepository customerRepository,
      CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  @Override
  @Transactional
  public void save(CustomerDto customerDto) {
    log.debug("Request to save Customer : {}", customerDto);
    Customer customer = customerMapper.toEntity(customerDto);
    customerRepository.save(customer);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CustomerDto> findByCreditId(Long id) {
    log.debug("Request to get Customer by creditId : {}", id);
    return customerRepository.findByCreditId(id)
        .map(customerMapper::toDto);
  }
}
