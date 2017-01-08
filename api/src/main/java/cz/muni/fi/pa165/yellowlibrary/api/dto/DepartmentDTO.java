package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Norbert Slivka
 */
public class DepartmentDTO {
  private Long id;
  @NotNull
  private String name;
  @NotNull
  @Size(min = 3, max = 3, message = "Size.DepartmentDTO.shortNameLength")
  private String shortName;

  public DepartmentDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DepartmentDTO)) {
      return false;
    }
    DepartmentDTO that = (DepartmentDTO) o;
    return Objects.equals(getShortName(), that.getShortName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getShortName());
  }
}
