package cz.muni.fi.pa165.yellowlibrary.api.dto;

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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LoanFilterDTO that = (LoanFilterDTO) o;

    if (user != null ? !user.equals(that.user) : that.user != null) return false;
    return filter != null ? filter.equals(that.filter) : that.filter == null;
  }

  @Override
  public int hashCode() {
    int result = user != null ? user.hashCode() : 0;
    result = 31 * result + (filter != null ? filter.hashCode() : 0);
    return result;
  }
}