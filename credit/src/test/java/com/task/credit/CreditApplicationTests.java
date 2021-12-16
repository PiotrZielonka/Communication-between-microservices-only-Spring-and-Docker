package com.task.credit;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class CreditApplicationTests {

  @Test
  void shouldRunCreditApplication() {
    CreditApplication.main(new String[] {});
  }

}
