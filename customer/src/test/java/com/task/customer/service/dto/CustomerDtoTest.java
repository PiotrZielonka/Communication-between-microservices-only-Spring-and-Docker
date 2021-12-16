package com.task.customer.service.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class CustomerDtoTest {

  @Test
  void simpleEqualsContracts() throws Exception {
    EqualsVerifier.simple().forClass(CustomerDto.class).verify();
  }

}