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

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;

/**
 * Created by Matej Gallo
 */

@Controller
@RequestMapping("/bookinstance")
public class BookInstanceController extends CommonController {

  final static Logger log = LoggerFactory.getLogger(BookInstanceController.class);

  @Inject
  private BookInstanceFacade bookInstanceFacade;

  @RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
  public String list(Model model) {
    return list("all", model);
  }

  @RequestMapping(value = "/list/{filter}", method = RequestMethod.GET)
  public String list(@PathVariable String filter, Model model) {
    switch(filter) {
      case "all":
        model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstances());
        break;
      case "borrowed":
        model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstancesByAvailability(
            BookInstanceAvailability.BORROWED));
        break;
      case "removed":
        model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstancesByAvailability(
            BookInstanceAvailability.REMOVED));
        break;
      case "available":
        model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstancesByAvailability(
            BookInstanceAvailability.AVAILABLE));
        break;

    }
    model.addAttribute("existsBook", false);
    return "bookinstance/list";
  }

  @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
  public String view(@PathVariable long id, Model model) {
    log.debug("view({})", id);
    model.addAttribute("bookinstance", bookInstanceFacade.findById(id));
    return "/bookinstance/view";
  }

  @RequestMapping(value = "/{id}/newstate", method = RequestMethod.GET)
  public String newBookState(@PathVariable Long id, Model model) {
    model.addAttribute("id", id);
    log.debug("newState({})", id);
    model.addAttribute("bookInstanceNewState", new BookInstanceNewStateDTO());
    return "/bookinstance/newState";
  }

  @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
  public String delete(@PathVariable Long id, Model model,
                       UriComponentsBuilder uriComponentsBuilder,
                       RedirectAttributes redirectAttributes) {
    log.debug("delete({})", id);
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(id);
    if(bookInstanceFacade.getAllBookInstancesByAvailability(BookInstanceAvailability.BORROWED)
        .contains(bookInstanceDTO)) {
      redirectAttributes.addFlashAttribute("alert_danger", "Loan with this book currently exists.");
      log.trace("Attempt to delete loaned book instance.");
      return "redirect:" + uriComponentsBuilder.path("/bookinstance/list").toUriString();
    }
    bookInstanceFacade.deleteBookInstance(id);
    redirectAttributes.addFlashAttribute("alert_success", "Book instance of \"" +
        bookInstanceDTO.getBook().getName() + "\" has been successfully deleted.");
    return "redirect:" + uriComponentsBuilder.path("/book/{bid}/bookinstances")
        .buildAndExpand(bookInstanceDTO.getBook().getId()).encode().toUriString();
  }

  @RequestMapping(value = "/{id}/changestate", method = RequestMethod.POST)
  public String changeState(@PathVariable Long id, @Valid @ModelAttribute("bookInstanceNewState")BookInstanceNewStateDTO formBean,
                            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                            UriComponentsBuilder uriComponentsBuilder) {
    log.debug("changeState(bookInstanceNewState={id})", id, formBean);

    if(bindingResult.hasErrors()) {
      for(ObjectError ge : bindingResult.getGlobalErrors()) {
        log.trace("ObjectError: {}", ge);
      }
      for(FieldError fe : bindingResult.getFieldErrors()) {
        model.addAttribute(fe.getField() + "_error", true);
        log.trace("FieldError: {}", fe);
      }
      return "/bookinstance/newState";
    }
    bookInstanceFacade.changeBookState(formBean);
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(id);
    String bookName = bookInstanceDTO.getBook().getName();
    redirectAttributes.addFlashAttribute("alert_success", "State of book instance of \"" + bookName +
        "\" has been successfully changed.");
    return "redirect:" + uriComponentsBuilder.path("/book/{id}/bookinstances").buildAndExpand(bookInstanceDTO.getBook().getId())
        .encode().toUriString();
  }

  @ModelAttribute("bookAvailabilities")
  public BookInstanceAvailability[] bookAvailabilities() {
    log.debug("bookAvailabilities()");
    return BookInstanceAvailability.values();
  }

}
