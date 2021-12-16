package com.task.credit.controller.errors;

public class ValidationProblemWithCustomer extends RuntimeException {

  public ValidationProblemWithCustomer() {
    super(String.format("Propably validation problem with customer"));
  }

}
