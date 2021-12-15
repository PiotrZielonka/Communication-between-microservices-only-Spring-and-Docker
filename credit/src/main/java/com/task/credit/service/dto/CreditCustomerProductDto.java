package com.task.credit.service.dto;

import com.task.customer.service.dto.CustomerDto;
import com.task.product.service.dto.ProductDto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreditCustomerProductDto {

  private Long id;

  @NotNull
  @Size(min = 0, max = 1000)
  private String creditName;

  private CustomerDto customer;

  private ProductDto product;

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

  public ProductDto getProductDto() {
    return product;
  }

  public void setProductDto(ProductDto product) {
    this.product = product;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CreditCustomerProductDto)) {
      return false;
    }

    CreditCustomerProductDto that = (CreditCustomerProductDto) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
    if (getCreditName() != null ? !getCreditName().equals(that.getCreditName())
        : that.getCreditName() != null) {
      return false;
    }
    if (customer != null ? !customer.equals(that.customer) : that.customer != null) {
      return false;
    }
    return product != null ? product.equals(that.product) : that.product == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getCreditName() != null ? getCreditName().hashCode() : 0);
    result = 31 * result + (customer != null ? customer.hashCode() : 0);
    result = 31 * result + (product != null ? product.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CreditCustomerProductDto{"
        + "id=" + id
        + ", creditName='" + creditName + '\''
        + ", customerDto=" + getCustomerDto()
        + ", productDto=" + getProductDto()
        + '}';
  }

}
