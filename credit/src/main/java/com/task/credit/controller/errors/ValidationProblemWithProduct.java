package com.task.credit.controller.errors;

public class ValidationProblemWithProduct extends RuntimeException {

  public ValidationProblemWithProduct() {
    super(String.format("Propably validation problem with product"));
  }

}
