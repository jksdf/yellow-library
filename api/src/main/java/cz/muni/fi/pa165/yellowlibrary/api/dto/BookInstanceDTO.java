package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;

/**
 * @author Matej Gallo
 */
public class BookInstanceDTO {

  private Long id;
  @NotNull
  private String bookState;
  private String version;
  @NotNull
  private BookInstanceAvailability bookAvailability;
  @NotNull
  private BookDTO book;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBookState() {
    return bookState;
  }

  public void setBookState(String bookState) {
    this.bookState = bookState;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public BookInstanceAvailability getBookAvailability() {
    return bookAvailability;
  }

  public void setBookAvailability(BookInstanceAvailability bookAvailability) {
    this.bookAvailability = bookAvailability;
  }

  public BookDTO getBook() {
    return book;
  }

  public void setBook(BookDTO book) {
    this.book = book;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !(o instanceof BookInstanceDTO)) {
      return false;
    }

    BookInstanceDTO that = (BookInstanceDTO) o;

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
    return getBook().equals(that.getBook());

  }

  @Override
  public int hashCode() {
    return Objects.hash(getBookState(), getBookAvailability(), getBook());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append((null != this.getBook() ? this.getBook().getName() : null) + ":");
    sb.append("\n\tID: " + this.getId());
    sb.append("\n\tVersion: " + this.getVersion());
    sb.append("\n\tState: " + this.getBookState());
    sb.append("\n");
    return sb.toString();
  }



}
