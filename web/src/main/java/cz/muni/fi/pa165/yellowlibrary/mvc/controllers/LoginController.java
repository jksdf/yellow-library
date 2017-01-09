package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for login.
 *
 * @author Jozef Zivcic
 */
@Controller
public class LoginController extends CommonController {

  private final static Logger log = LoggerFactory.getLogger(LoginController.class);

  /**
   * Displays login page.
   *
   * @param model Model.
   * @return JSP which to display.
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginGet(Model model) {
    log.info("LoginController.loginGet()");
    return "authentication/login";
  }

  /**
   * POST method for sending credentials from user web browser.
   *
   * @param model Model.
   * @return JSP which to display.
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String loginPost(Model model) {
    log.info("LoginController.loginPost()");
    return "/login";
  }
}
