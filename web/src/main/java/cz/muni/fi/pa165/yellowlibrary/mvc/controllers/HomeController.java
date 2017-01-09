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
@RequestMapping(value = {"", "/", "/home"})
public class HomeController extends CommonController {

  private final static Logger log = LoggerFactory.getLogger(HomeController.class);

  @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
  public String index(Model model) {
    log.info("HomeController.index()");
    return "home/index";
  }
}
