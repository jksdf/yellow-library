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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewAvailabilityDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;

/**
 * Created by Matej Gallo
 *
 * Provides methods to handle book instance requests.
 */

@Controller
@RequestMapping("/bookinstance")
public class BookInstanceController extends CommonController {

  final static Logger log = LoggerFactory.getLogger(BookInstanceController.class);

  @Inject
  private BookInstanceFacade bookInstanceFacade;

  @Inject
  private BookFacade bookFacade;


  /**
   * Returns a list of all book instances.
   * If filter is provided, shows only instances with given availability.
   * If bid is provided, shows only instances of particular book.
   * @param filter optional: available, borrowed, removed
   * @param bid optional: BookID of a book
   */
  @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
  public String list(@RequestParam(required = false) String filter,
                     @RequestParam(required = false) Long bid,
                     Model model) {
    log.debug("list{}", bid, filter);

    if(filter == null)
      filter = "all";

    switch(filter) {
      case "borrowed":
        if (bid == null)
          model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstancesByAvailability(
            BookInstanceAvailability.BORROWED));
        else
          model.addAttribute("bookinstances", bookInstanceFacade.getAllCopiesByAvailability(bid,
              BookInstanceAvailability.BORROWED));
        break;
      case "removed":
        if (bid == null)
          model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstancesByAvailability(
            BookInstanceAvailability.REMOVED));
        else
          model.addAttribute("bookinstances", bookInstanceFacade.getAllCopiesByAvailability(bid,
              BookInstanceAvailability.REMOVED));
        break;
      case "available":
        if (bid == null)
          model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstancesByAvailability(
            BookInstanceAvailability.AVAILABLE));
        else
          model.addAttribute("bookinstances", bookInstanceFacade.getAllCopiesByAvailability(bid,
              BookInstanceAvailability.AVAILABLE));
        break;
      case "all":
        if (bid == null)
          model.addAttribute("bookinstances", bookInstanceFacade.getAllBookInstances());
        else
          model.addAttribute("bookinstances", bookInstanceFacade.getAllCopies(bid));
        break;

    }
    model.addAttribute("existsBook", false);
    return "bookinstance/list";
  }

  /**
   * Provides form to change state of a book instance
   * @param id id of a book instance
   */
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
  public String newBookState(@PathVariable Long id,
                             @RequestParam String attr,
                             Model model,
                             UriComponentsBuilder uriComponentsBuilder) {
    model.addAttribute("id", id);
    log.debug("edit({})", id, attr);

    if(attr.equals("state")) {
      model.addAttribute("bookInstanceNewState", new BookInstanceNewStateDTO());
      return "/bookinstance/newState";
    } else if (attr.equals("availability")) {
      model.addAttribute("bookInstanceNewAvailability", new BookInstanceNewAvailabilityDTO());
      return "/bookinstance/newAvailability";
    } else {
      model.addAttribute("alert_danger", "Unknown attribute " + attr);
      return "redirect:" + uriComponentsBuilder.path("/bookinstance/list?={bid}")
          .buildAndExpand(bookInstanceFacade.findById(id).getBook().getId()).encode().toUriString();
    }
  }

  /**
   * Deletes a particular book instance that is NOT currently loaned.
   * @param id  ID of a book instance to delete
   */
  @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
  public String delete(@PathVariable Long id,
                       Model model,
                       UriComponentsBuilder uriComponentsBuilder,
                       RedirectAttributes redirectAttributes) {
    log.debug("delete({})", id);
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(id);
    if(bookInstanceFacade.getAllBookInstancesByAvailability(BookInstanceAvailability.BORROWED)
        .contains(bookInstanceDTO)) {
      redirectAttributes.addFlashAttribute("alert_danger", "Loan with this book currently exists.");
      log.trace("Attempt to delete loaned book instance.");
      return "redirect:" + uriComponentsBuilder.path("/bookinstance")
          .queryParam("bid", bookInstanceDTO.getBook().getId())
          .toUriString();
    }
    bookInstanceFacade.deleteBookInstance(id);
    redirectAttributes.addFlashAttribute("alert_success", "Book instance has been successfully deleted.");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance")
        .queryParam("bid", bookInstanceDTO.getBook().getId())
        .toUriString();
  }

  /**
   * Validates form and changes the state of a book instance
   * @param id id of a book instance to change
   */
  @RequestMapping(value = "/{id}/change-state", method = RequestMethod.POST)
  public String changeState(@PathVariable Long id,
                            @Valid @ModelAttribute("bookInstanceNewState")BookInstanceNewStateDTO formBean,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes,
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
    redirectAttributes.addFlashAttribute("alert_success", "State has been successfully changed.");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance/list").queryParam("bid", bookInstanceDTO.getBook().getId()).toUriString();
  }

  /**
   * Validates form and changes the availability of a book instance
   * @param id id of a book instance to change
   */
  @RequestMapping(value = "/{id}/change-availability", method = RequestMethod.POST)
  public String changeAvailability(@PathVariable Long id,
                            @Valid @ModelAttribute("bookInstanceNewAvailability")BookInstanceNewAvailabilityDTO formBean,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            UriComponentsBuilder uriComponentsBuilder) {
    log.debug("changeState(bookInstanceNewAvailability={id})", id, formBean);

    if(bindingResult.hasErrors()) {
      for(ObjectError ge : bindingResult.getGlobalErrors()) {
        log.trace("ObjectError: {}", ge);
      }
      for(FieldError fe : bindingResult.getFieldErrors()) {
        model.addAttribute(fe.getField() + "_error", true);
        log.trace("FieldError: {}", fe);
      }
      return "/bookinstance/newAvailability";
    }
    bookInstanceFacade.changeBookAvailability(formBean);
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(id);
    redirectAttributes.addFlashAttribute("alert_success", "Availability has been successfully changed.");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance/list").queryParam("bid", bookInstanceDTO.getBook().getId()).toUriString();
  }


  /**
   * Provides form to add new book instance
   * @param bid ID of a book
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String newBookInstance(@RequestParam Long bid, Model model) {
    model.addAttribute("bookId", bid);
    log.debug("add()");
    model.addAttribute("bookInstanceCreate", new BookInstanceDTO());
    model.addAttribute("bookName", bookFacade.getBook(bid).getName());
    model.addAttribute("bookAuthor", bookFacade.getBook(bid).getAuthor());
    return "bookinstance/new";
  }

  /**
   * Validates bean and creates new book instance
   * @param bid ID of a book
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String create(@RequestParam Long bid,
                       @Valid @ModelAttribute("bookInstanceCreate") BookInstanceCreateDTO formBean,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    log.debug("create(bookInstanceCreate={})", formBean);
    model.addAttribute("bookId", bid);
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
    BookInstanceDTO bookInstance= bookInstanceFacade.findById(id);
    redirectAttributes.addFlashAttribute("alert_success", "New book has been successfully created");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance").queryParam("bid", bid).toUriString();
  }

  /**
   * List of all possible options for availability
   */
  @ModelAttribute("bookAvailabilities")
  public BookInstanceAvailability[] bookAvailabilities() {
    log.debug("bookAvailabilities()");
    return BookInstanceAvailability.values();
  }
}
