package com.task.customer.service.dto;

import com.task.customer.domain.Customer;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class CustomerDtoTest {

  @Test
  void simpleEqualsContracts() throws Exception {
    EqualsVerifier.simple().forClass(CustomerDto.class).verify();
  }

}