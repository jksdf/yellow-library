package cz.muni.fi.pa165.yellowlibrary.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Norbert Slivka
 */
public class RestError {
  private List<String> errors = new ArrayList<>();

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public List<String> getErrors() {
    return errors;
  }
}
