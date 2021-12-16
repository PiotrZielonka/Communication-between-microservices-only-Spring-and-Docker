package com.task.product.controller.errors;

public class IdInDtoExist extends RuntimeException {

  public IdInDtoExist() {
    super(String.format("A new product cannot already have an Id Id in Dto exists"));
  }

}
