package cz.muni.fi.pa165.yellowlibrary.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by reyvateil on 15.12.2016.
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Something went wrong...")
public class YellowServiceException extends RuntimeException {
}
