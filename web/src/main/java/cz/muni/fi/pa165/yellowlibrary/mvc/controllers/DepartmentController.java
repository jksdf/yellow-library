package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.facade.DepartmentFacade;

/**
 * @author Norbert Slivka
 */
@Controller
@RequestMapping(value = {"/department"})
public class DepartmentController extends CommonController {

  @Inject
  private DepartmentFacade departmentFacade;

  @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
  public String list(Model model) {
    model.addAttribute("departments", departmentFacade.getAll());
    return "department/list";
  }
}
