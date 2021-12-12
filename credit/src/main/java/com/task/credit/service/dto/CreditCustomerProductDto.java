package com.task.credit.service.dto;

import com.task.customer.service.dto.CustomerDto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreditCustomerProductDto {

  private Long id;

  @NotNull
  @Size(min = 0, max = 1000)
  private String creditName;

  private CustomerDto customer;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreditName() {
    return creditName;
  }

  public void setCreditName(String creditName) {
    this.creditName = creditName;
  }

  public CustomerDto getCustomerDto() {
    return customer;
  }

  public void setCustomerDto(CustomerDto customer) {
    this.customer = customer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CreditCustomerProductDto creditCustomerProductDto = (CreditCustomerProductDto) o;

    if (!id.equals(creditCustomerProductDto.id)) {
      return false;
    }
    return creditName.equals(creditCustomerProductDto.creditName);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + creditName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CreditCustomerProductDto{"
        + "id=" + id
        + ", creditName='" + creditName + '\''
        + ", customerDto=" + getCustomerDto()
        + '}';
  }

}
