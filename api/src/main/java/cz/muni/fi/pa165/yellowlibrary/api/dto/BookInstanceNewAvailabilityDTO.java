package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;

/**
 * Created by reyvateil on 16.12.2016.
 */
public class BookInstanceNewAvailabilityDTO {

  private Long id;

  @NotNull
  private BookInstanceAvailability bookAvailability;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BookInstanceAvailability getBookAvailability() {
    return bookAvailability;
  }

  public void setBookAvailability(
      BookInstanceAvailability bookAvailability) {
    this.bookAvailability = bookAvailability;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BookInstanceNewAvailabilityDTO)) {
      return false;
    }

    BookInstanceNewAvailabilityDTO that = (BookInstanceNewAvailabilityDTO) o;

    return getBookAvailability() == that.getBookAvailability();

  }

  @Override
  public int hashCode() {
    return Objects.hash(getBookAvailability());
  }
}
