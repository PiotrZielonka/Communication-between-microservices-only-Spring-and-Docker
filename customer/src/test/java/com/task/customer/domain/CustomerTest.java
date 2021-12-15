package com.task.customer.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class CustomerTest {

  @Test
  void simpleEqualsContracts() throws Exception {
    EqualsVerifier.simple().forClass(Customer.class).verify();
  }

}
