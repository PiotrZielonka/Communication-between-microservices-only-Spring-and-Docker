package com.task.product.service.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDto {

  private Long id;

  @NotNull
  @Size(min = 0, max = 1000)
  private String name;

  @NotNull
  @Min(value = 0)
  @Max(value = 1000000000, message = "Product value can't be bigger than 1000000000")
  private Integer productValue;

  @NotNull
  private Long creditId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getProductValue() {
    return productValue;
  }

  public void setProductValue(Integer productValue) {
    this.productValue = productValue;
  }

  public Long getCreditId() {
    return creditId;
  }

  public void setCreditId(Long creditId) {
    this.creditId = creditId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProductDto)) {
      return false;
    }

    ProductDto that = (ProductDto) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
    if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
      return false;
    }
    if (getProductValue() != null ? !getProductValue().equals(that.getProductValue())
        : that.getProductValue() != null) {
      return false;
    }
    return getCreditId() != null ? getCreditId().equals(that.getCreditId())
        : that.getCreditId() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getProductValue() != null ? getProductValue().hashCode() : 0);
    result = 31 * result + (getCreditId() != null ? getCreditId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ProductDto{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", productValue=" + productValue
        + ", creditId=" + creditId
        + '}';
  }
}
