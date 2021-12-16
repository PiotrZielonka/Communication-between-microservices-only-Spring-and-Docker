package com.task.customer.controller.errors;

public class IdInDtoExist extends RuntimeException {

  public IdInDtoExist() {
    super(String.format("A new customer cannot already have an Id Id in Dto exists"));
  }

}
