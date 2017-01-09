package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for upading book instance state
 *
 * Created by Matej Gallo
 */

public class BookInstanceNewStateDTO {
  private Long id;

  @NotNull
  @Size(min=3, max=30)
  private String bookState;

  /**
   * Retrieves book instance ID
   * @return Long
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the book instance ID
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

  @Override
  public int hashCode() {
    return Objects.hash(getBookState());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if(obj == null)
      return false;

    if(!(obj instanceof BookInstanceNewStateDTO))
      return false;

    BookInstanceNewStateDTO otherDTO = (BookInstanceNewStateDTO) obj;

    return getBookState().equals(otherDTO);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("BookInstanceNewStateDTO {");
    sb.append("\n\tbookInstanceID: " + getId());
    sb.append("\n\tbookState: " + getBookState());
    return sb.toString();
  }
}
