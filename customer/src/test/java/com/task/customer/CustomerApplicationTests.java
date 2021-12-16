package com.task.customer;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class CustomerApplicationTests {

  @Test
  void shouldRunCustomerApplication() {
    CustomerApplication.main(new String[] {});
  }

}
