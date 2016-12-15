package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.service.BookInstanceService;

/**
 * Created by Matej Gallo
 */

@Controller
@RequestMapping("/bookinstance")
public class BookInstanceController {

  final static Logger log = LoggerFactory.getLogger(BookInstanceController.class);

  @Inject
  private BookInstanceFacade bookInstanceFacade;

  @Inject
  private BookInstanceService bookInstanceService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String list(Model model) {
    model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstances());
    return "bookinstance/list";
  }

  @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
  public String view(@PathVariable long id, Model model) {
    log.debug("view({})", id);
    model.addAttribute("bookinstance", bookInstanceFacade.findById(id));
    return "bookinstance/view";
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String newBookInstance(Model model) {
    log.debug("add()");
    model.addAttribute("bookInstanceCreate", new BookInstanceDTO());
    return "bookinstance/new";
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  public String delete(@PathVariable long id, Model model,
                       UriComponentsBuilder uriComponentsBuilder,
                       RedirectAttributes redirectAttributes) {
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(id);
    bookInstanceFacade.deleteBookInstance(id);
    log.debug("delete({})", id);
    redirectAttributes.addFlashAttribute("alert_success", "BookInstance \"" +
        bookInstanceDTO.getBook().getName() + " " + bookInstanceDTO.getId() +
        "was deleted.");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance/list").toUriString();
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String create(@Valid @ModelAttribute("bookInstanceCreate") BookInstanceCreateDTO formBean,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    log.debug("create(bookInstanceCreate={}", formBean);

    if(bindingResult.hasErrors()) {
      for(ObjectError ge : bindingResult.getGlobalErrors()) {
        log.trace("ObjectError: {}", ge);
      }
      for(FieldError fe : bindingResult.getFieldErrors()) {
        model.addAttribute(fe.getField() + "_error", true);
        log.trace("FieldError: {}", fe);
      }
      return "bookinstance/new";
    }
    Long id = bookInstanceFacade.createBookInstance(formBean);
    redirectAttributes.addFlashAttribute("alert_success", "Book instance " + id + "was created");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance/view/{id}").buildAndExpand(id).encode().toUriString();
  }

  @ModelAttribute("bookAvailabilities")
  public BookInstanceAvailability[] bookAvailabilities() {
    log.debug("bookAvailabilities()");
    return BookInstanceAvailability.values();
  }

}
