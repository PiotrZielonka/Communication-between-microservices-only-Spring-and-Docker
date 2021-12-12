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
  @Max(value = 1000000000)
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ProductDto that = (ProductDto) o;

    if (!id.equals(that.id)) {
      return false;
    }
    if (!name.equals(that.name)) {
      return false;
    }
    if (!productValue.equals(that.productValue)) {
      return false;
    }
    return creditId.equals(that.creditId);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + productValue.hashCode();
    result = 31 * result + creditId.hashCode();
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
