package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;

/**
 * DTO for book instance
 *
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

  /**
   * Retrieves ID of book instance
   * @return Long
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of book instance
   * @param id Long
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Retrieves the book instance state
   * @return String
   */
  public String getBookState() {
    return bookState;
  }

  /**
   * Sets the book instance state
   * @param bookState String
   */
  public void setBookState(String bookState) {
    this.bookState = bookState;
  }

  /**
   * Retrieves the book instance version
   * @return String
   */
  public String getVersion() {
    return version;
  }

  /**
   * Sets the book instance version
   * @param version String
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Retrieves the book instance availability
   * @return BookInstanceAvailability
   */
  public BookInstanceAvailability getBookAvailability() {
    return bookAvailability;
  }

  /**
   * Sets the book instance availability
   * @param bookAvailability BookInstanceAvailability
   */
  public void setBookAvailability(BookInstanceAvailability bookAvailability) {
    this.bookAvailability = bookAvailability;
  }

  /**
   * Returns the book instance parent book
   * @return BookDTO
   */
  public BookDTO getBook() {
    return book;
  }

  /**
   * Sets the book instance parent book
   * @param book BookDTO
   */
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
    sb.append("\n\tBook: " + this.getBook().getName());
    sb.append("\n\tID: " + this.getId());
    sb.append("\n\tVersion: " + this.getVersion());
    sb.append("\n\tState: " + this.getBookState());
    sb.append("\n");
    return sb.toString();
  }



}
