package cz.muni.fi.pa165.yellowlibrary.api.exceptions;

public class BookInstanceNotAvailableException extends RuntimeException  {
  public BookInstanceNotAvailableException() {
    super();
  }

  public BookInstanceNotAvailableException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public BookInstanceNotAvailableException(String message, Throwable cause) {
    super(message, cause);
  }

  public BookInstanceNotAvailableException(String message) {
    super(message);
  }

  public BookInstanceNotAvailableException(Throwable cause) {
    super(cause);
  }

}
