package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;
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

  @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
  public String createGet(Model model) {
    model.addAttribute("department", new DepartmentDTO());
    return "department/create";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String create(@Valid @ModelAttribute("department") DepartmentDTO data,
                       BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    if (bindingResult.hasErrors()) {
      return "redirect:department/create";
    }
    departmentFacade.create(data);
    redirectAttributes.addFlashAttribute("alert_success",
        String.format("New department %s has been successfully created", data.getName()));
    return "redirect:" + uriComponentsBuilder.path("/department/list").toUriString();
  }
}
