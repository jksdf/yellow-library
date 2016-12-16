package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import cz.muni.fi.pa165.yellowlibrary.mvc.exceptions.ResourceNotFoundException;

/**
 * @author Jozef Zivcic
 */
@ControllerAdvice
public class ExceptionController extends CommonController {

  private final static Logger log = LoggerFactory.getLogger(ExceptionController.class);

  @ExceptionHandler(value = ResourceNotFoundException.class)
  public String handleResourceNotFoundException() {
    log.info("ExceptionController.handleResourceNotFoundException()");
    return "exceptions/notfound";
  }
}
