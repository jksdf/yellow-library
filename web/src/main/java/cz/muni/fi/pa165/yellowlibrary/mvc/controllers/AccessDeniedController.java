package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jozef Zivcic
 */
@Controller
public class AccessDeniedController extends CommonController {

  final static Logger log = LoggerFactory.getLogger(LoginController.class);

  @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
  public String accessDenied(Model model) {
    log.info("LoginController.accessDenied()");
    return "authentication/accessdenied";
  }
}
