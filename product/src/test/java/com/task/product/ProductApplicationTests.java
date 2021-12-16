package com.task.product;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ProductApplicationTests {

  @Test
  void shouldRunProductApplication() {
    ProductApplication.main(new String[] {});
  }

}
