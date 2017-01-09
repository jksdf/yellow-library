package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

/**
 * @author cokinova
 */
public class LoanFilterDTO {
  private UserDTO user;
  private String filter;

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LoanFilterDTO)) {
      return false;
    }

    LoanFilterDTO that = (LoanFilterDTO) o;
    return Objects.equals(getUser(), that.getUser()) &&
        Objects.equals(getFilter(), that.getFilter());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUser(), getFilter());
  }
}