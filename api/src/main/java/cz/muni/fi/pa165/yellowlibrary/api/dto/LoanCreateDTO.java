package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * @author cokinova
 */
public class LoanCreateDTO {
  @NotNull
  private Date dateFrom;

  @NotNull
  private int loanLength;

  @NotNull
  private String loanState;

  @NotNull
  private UserDTO user;

  @NotNull
  private BookInstanceDTO bookInstance;

  public Date getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(Date dateFrom) {
    this.dateFrom = dateFrom;
  }


  public int getLoanLength() {
    return loanLength;
  }

  public void setLoanLength(int loanLength) {
    this.loanLength = loanLength;
  }

  public String getLoanState() {
    return loanState;
  }

  public void setLoanState(String loanState) {
    this.loanState = loanState;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public BookInstanceDTO getBookInstance() {
    return bookInstance;
  }

  public void setBookInstance(BookInstanceDTO bookInstance) {
    this.bookInstance = bookInstance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !(o instanceof LoanDTO)) {
      return false;
    }

    LoanCreateDTO that = (LoanCreateDTO) o;

    if (!getDateFrom().equals(that.getDateFrom())) {
      return false;
    }
    if (!getUser().equals(that.getUser())) {
      return false;
    }
    return getBookInstance().equals(that.getBookInstance());

  }

  @Override
  public int hashCode() {
    int result = getDateFrom().hashCode();
    result = 31 * result + getUser().hashCode();
    result = 31 * result + getBookInstance().hashCode();
    return result;
  }
}
