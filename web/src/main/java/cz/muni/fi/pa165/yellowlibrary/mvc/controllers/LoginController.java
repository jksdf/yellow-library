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
public class LoginController extends CommonController {

  final static Logger log = LoggerFactory.getLogger(LoginController.class);

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginGet(Model model) {
    log.info("LoginController.loginGet()");
    return "authentication/login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String loginPost(Model model) {
    log.info("LoginController.loginPost()");
    return "/login";
  }
}
