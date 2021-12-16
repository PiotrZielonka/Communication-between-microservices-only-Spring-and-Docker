package com.task.credit.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class CreditTest {

  @Test
  void simpleEqualsContracts() throws Exception {
    EqualsVerifier.simple().forClass(Credit.class).verify();
  }

}
