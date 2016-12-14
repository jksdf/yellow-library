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
@RequestMapping("/user")
public class UserController {

  final static Logger log = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
  public String index(Model model) {
    log.info("UserController.index()");
    return "user/user";
  }
}
