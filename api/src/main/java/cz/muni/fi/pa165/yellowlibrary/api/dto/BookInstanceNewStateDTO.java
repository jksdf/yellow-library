package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * Created by Matej Gallo
 */

public class BookInstanceNewStateDTO {
  private Long id;

  @NotNull
  private String bookState;

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
