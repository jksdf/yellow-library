package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;

/**
 * @author Jozef Zivcic
 */
@Controller
@RequestMapping("/user")
public class UserController extends CommonController {

  private final static Logger log = LoggerFactory.getLogger(UserController.class);

  @Inject
  private UserFacade userFacade;

  @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
  public String index(Model model) {
    log.info("UserController.index()");
    return "user/user";
  }
}
