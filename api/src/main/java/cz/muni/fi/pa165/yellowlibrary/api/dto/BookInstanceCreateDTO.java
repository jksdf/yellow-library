package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;

/**
 * @author Matej Gallo
 */
public class BookInstanceCreateDTO {

  @NotNull
  @Size(min = 3, max = 30)
  private String bookState;

  @NotNull
  private BookInstanceAvailability bookAvailability;

  private String version;

  @NotNull
  private Long bookId;

  public String getBookState() {
    return bookState;
  }

  public void setBookState(String bookState) {
    this.bookState = bookState;
  }

  public BookInstanceAvailability getBookAvailability() {
    return bookAvailability;
  }

  public void setBookAvailability(BookInstanceAvailability bookAvailability) {
    this.bookAvailability = bookAvailability;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Long getBookId() {
    return bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !(o instanceof BookInstanceCreateDTO)) {
      return false;
    }

    BookInstanceCreateDTO that = (BookInstanceCreateDTO) o;

    if (!getBookState().equals(that.getBookState())) {
      return false;
    }
    if (getVersion() != null ? !getVersion().equals(that.getVersion())
        : that.getVersion() != null) {
      return false;
    }
    if (getBookAvailability() != that.getBookAvailability()) {
      return false;
    }
    return getBookId().equals(that.getBookId());

  }

  @Override
  public int hashCode() {
    return Objects.hash(getBookState(), getBookAvailability(), getBookId());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Book ID: " + this.getBookId());
    sb.append("\n\tVersion: " + this.getVersion());
    sb.append("\n\tState: " + this.getBookState());
    sb.append("\n");
    return sb.toString();
  }

}
