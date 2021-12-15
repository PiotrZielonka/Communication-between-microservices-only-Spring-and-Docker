package com.task.credit.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public class Credit {

  @Id
  private Long id;

  @NotNull
  @Size(min = 0, max = 1000)
  private String creditName;

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

  public Credit() {

  }

  public Credit(Long id, String creditName) {
    this.id = id;
    this.creditName = creditName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Credit)) {
      return false;
    }

    Credit credit = (Credit) o;

    if (getId() != null ? !getId().equals(credit.getId()) : credit.getId() != null) {
      return false;
    }
    return getCreditName() != null ? getCreditName().equals(credit.getCreditName())
        : credit.getCreditName() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getCreditName() != null ? getCreditName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Credit{"
        + "id=" + id
        + ", creditName='" + creditName + '\''
        + '}';
  }
}
