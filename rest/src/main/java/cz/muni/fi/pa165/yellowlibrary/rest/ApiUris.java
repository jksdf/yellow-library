package cz.muni.fi.pa165.yellowlibrary.rest;

/**
 * @author Norbert Slivka
 */
public final class ApiUris {
  public static final String ROOT_URI_BOOK = "/book";
  public static final String ROOT_URI_USER = "/user";
  public static final String ROOT_URI_DEPARTMENT = "/department";

  private ApiUris() {
    throw new AssertionError("This is not the class you are looking for");
  }
}
