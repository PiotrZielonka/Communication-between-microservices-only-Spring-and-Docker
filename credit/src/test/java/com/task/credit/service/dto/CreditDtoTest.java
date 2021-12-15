package com.task.credit.service.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class CreditDtoTest {

  @Test
  void simpleEqualsContractsCreditDto() throws Exception {
    EqualsVerifier.simple().forClass(CreditDto.class).verify();
  }

  @Test
  void simpleEqualsContractsCreditCustomerProductDto() throws Exception {
    EqualsVerifier.simple().forClass(CreditCustomerProductDto.class).verify();
  }

}
