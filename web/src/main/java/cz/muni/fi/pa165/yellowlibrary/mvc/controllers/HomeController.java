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
@RequestMapping(value = {"", "/", "/home"})
public class HomeController extends CommonController {

  final static Logger log = LoggerFactory.getLogger(HomeController.class);

  @Inject
  private UserFacade userFacade;

  @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
  public String index(Model model) {
    model.addAttribute("title", "Yellow library");
    log.debug(String.valueOf(userFacade.findAllUsers().size()));
    model.addAttribute("users", userFacade.findAllUsers());
    return "home/index";
  }
}
