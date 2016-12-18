package cz.muni.fi.pa165.yellowlibrary.api.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 * @author cokinova
 */
public class LoanCreateDTO {
  private Date dateFrom;

  private Date returnDate;

  @NotNull
  private int loanLength;

  @NotEmpty
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

  public Date getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LoanCreateDTO that = (LoanCreateDTO) o;

    if (getDateFrom() != null ? !getDateFrom().equals(that.getDateFrom())
        : that.getDateFrom() != null) {
      return false;
    }
    if (getUser() != null ? !getUser().equals(that.getUser()) : that.getUser() != null) {
      return false;
    }
    return getBookInstance() != null ? getBookInstance().equals(that.getBookInstance())
        : that.getBookInstance() == null;

  }

  @Override
  public int hashCode() {
    int result = getDateFrom() != null ? getDateFrom().hashCode() : 0;
    result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
    result = 31 * result + (getBookInstance() != null ? getBookInstance().hashCode() : 0);
    return result;
  }

  @Override
  public String toString(){
    return "id:"+ (user.getId() == null ? "null": user.getId()) +
        "user: "+ user.getName() +
        "book: "+ bookInstance.getBook().getName() +
        "from:" + getDateFrom();
  }

}
