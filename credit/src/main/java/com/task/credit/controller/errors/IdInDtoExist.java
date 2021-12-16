package com.task.credit.controller.errors;

public class IdInDtoExist extends RuntimeException {

  public IdInDtoExist() {
    super(String.format("A new credit customer product cannot already have an Id Id in Dto exists"));
  }

}
