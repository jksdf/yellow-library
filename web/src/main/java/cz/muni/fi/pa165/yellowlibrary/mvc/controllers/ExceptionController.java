package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for Exceptions.
 *
 * @author Jozef Zivcic
 */
@Controller
public class ExceptionController extends CommonController {

  private final static Logger log = LoggerFactory.getLogger(ExceptionController.class);

  /**
   * Displays page not found poge.
   *
   * @return JSP which to display.
   */
  @RequestMapping(value = "/not_found", method = RequestMethod.GET)
  public String handleResourceNotFoundException() {
    log.info("ExceptionController.handleResourceNotFoundException()");
    return "exceptions/notfound";
  }
}
