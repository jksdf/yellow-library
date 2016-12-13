package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jozef Zivcic
 */
@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {

  @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
  public String index(Model model) {
    model.addAttribute("title", "Yellow library");
    return "home/index";
  }
}
