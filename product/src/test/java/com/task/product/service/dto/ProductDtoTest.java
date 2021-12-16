package com.task.product.service.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ProductDtoTest {

  @Test
  void simpleEqualsContracts() throws Exception {
    EqualsVerifier.simple().forClass(ProductDto.class).verify();
  }

}
