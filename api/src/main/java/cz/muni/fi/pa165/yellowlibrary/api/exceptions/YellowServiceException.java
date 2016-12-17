package cz.muni.fi.pa165.yellowlibrary.api.exceptions;

/**
 * Exceptions for Yellow Library
 */
public class YellowServiceException extends RuntimeException {

  public YellowServiceException() {
    super();
  }

  public YellowServiceException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public YellowServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public YellowServiceException(String message) {
    super(message);
  }

  public YellowServiceException(Throwable cause) {
    super(cause);
  }

}
