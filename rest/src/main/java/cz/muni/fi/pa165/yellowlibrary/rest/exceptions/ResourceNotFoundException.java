package cz.muni.fi.pa165.yellowlibrary.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Norbert Slivka
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested resource was not found")
public class ResourceNotFoundException extends RuntimeException {
}
