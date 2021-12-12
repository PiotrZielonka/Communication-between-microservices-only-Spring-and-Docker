package com.task.customer.controller;

import com.task.customer.controller.errors.BadRequestAlertException;
import com.task.customer.service.CustomerService;
import com.task.customer.service.dto.CustomerDto;
import java.net.URISyntaxException;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  private final Logger log = LoggerFactory.getLogger(CustomerController.class);

  private static final String ENTITY_NAME = "customer";

  private final CustomerService customerService;


  public CustomerController(
      CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/customers")
  public void createCustomer(
      @Valid @RequestBody CustomerDto customerDto) throws URISyntaxException {
    log.debug("REST request to save Customer : {}", customerDto);
    if (customerDto.getId() != null) {
      throw new BadRequestAlertException(
          "A new customer cannot already have an ID", ENTITY_NAME, "idexists");
    }
    customerService.save(customerDto);
  }

  @GetMapping("/customers/{id}")
  public Optional<CustomerDto> getCustomerByCreditId(@PathVariable Long id) {
    log.debug("REST request to get Customer by CreditId");
    return customerService.findByCreditId(id);
  }

}
