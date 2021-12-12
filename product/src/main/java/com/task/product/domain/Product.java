package com.task.product.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public class Product {

  @Id
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

  public Product() {
  }

  public Product(Long id, String name, Integer productValue, Long creditId) {
    this.id = id;
    this.name = name;
    this.productValue = productValue;
    this.creditId = creditId;
  }

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

    Product product = (Product) o;

    if (!id.equals(product.id)) {
      return false;
    }
    if (!name.equals(product.name)) {
      return false;
    }
    if (!productValue.equals(product.productValue)) {
      return false;
    }
    return creditId.equals(product.creditId);
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
    return "Product{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", productValue=" + productValue
        + ", creditId=" + creditId
        + '}';
  }
}
