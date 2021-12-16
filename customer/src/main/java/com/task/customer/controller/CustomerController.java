package com.task.customer.controller;

import com.task.customer.controller.errors.IdInDtoExist;
import com.task.customer.service.CustomerService;
import com.task.customer.service.dto.CustomerDto;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  private final Logger log = LoggerFactory.getLogger(CustomerController.class);

  private final CustomerService customerService;


  public CustomerController(
      CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/customers")
  public void createCustomer(@Valid @RequestBody CustomerDto customerDto) {
    log.debug("REST request to save Customer : {}", customerDto);
    if (customerDto.getId() != null) {
      throw new IdInDtoExist();
    }
    customerService.save(customerDto);
  }

  @GetMapping("/customers/{id}")
  public ResponseEntity<CustomerDto> getCustomerByCreditId(@PathVariable Long id) {
    log.debug("REST request to get Customer by CreditId");
    Optional<CustomerDto> customerDto = customerService.findByCreditId(id);
    return ResponseEntity.of(customerDto);
  }
}
