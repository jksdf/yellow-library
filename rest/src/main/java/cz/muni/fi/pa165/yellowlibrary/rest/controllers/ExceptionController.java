package cz.muni.fi.pa165.yellowlibrary.rest.controllers;

import com.google.common.collect.ImmutableList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cz.muni.fi.pa165.yellowlibrary.rest.RestError;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.ResourceAlreadyExists;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.ResourceNotFoundException;

/**
 * @author Norbert Slivka
 */
@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  RestError handleException(ResourceNotFoundException ex) {
    RestError error = new RestError();
    error.setErrors(ImmutableList.of("Unable to find the requested resource."));
    return error;
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ResponseBody
  RestError handleException(ResourceAlreadyExists ex) {
    RestError error = new RestError();
    error.setErrors(ImmutableList.of("Resource already exists."));
    return error;
  }

}
