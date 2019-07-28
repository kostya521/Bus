package com.goeuro.challenges.busroutesservice.exceptions;

public class DataFileException extends RuntimeException {

  public DataFileException() {
    super();
  }

  public DataFileException(String message) {
    super(message);
  }

  public DataFileException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataFileException(Throwable cause) {
    super(cause);
  }

  protected DataFileException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
