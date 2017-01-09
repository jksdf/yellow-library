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

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.exceptions.YellowServiceException;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.LoanFacade;

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

  @Inject
  private LoanFacade loanFacade;

  /**
   * Returns a list of all book instances.
   * If filter is provided, shows only instances with given availability.
   * If bid is provided, shows only instances of particular book.
   * @param filter optional: available, borrowed, removed
   * @param bid optional: BookID of a book
   */
  @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
  public String list(@RequestParam(required = false, defaultValue = "all") String filter,
                     @RequestParam(required = false) Long bid,
                     Model model) {
    log.debug("list{}", bid, filter);

    List<BookInstanceDTO> result;

    switch(filter) {
      case "borrowed":
        if (bid == null) {
          result = bookInstanceFacade.getAllBookInstancesByAvailability(BookInstanceAvailability.BORROWED);
        }
        else {
          result = bookInstanceFacade.getAllCopiesByAvailability(bid, BookInstanceAvailability.BORROWED);
        }
        break;
      case "removed":
        if (bid == null) {
          result = bookInstanceFacade.getAllBookInstancesByAvailability(BookInstanceAvailability.REMOVED);
        }
        else {
          result = bookInstanceFacade.getAllCopiesByAvailability(bid,BookInstanceAvailability.REMOVED);
        }
        break;
      case "available":
        if (bid == null) {
          result = bookInstanceFacade.getAllBookInstancesByAvailability(BookInstanceAvailability.AVAILABLE);
        } else {
          result = bookInstanceFacade.getAllCopiesByAvailability(bid, BookInstanceAvailability.AVAILABLE);
        }
        break;
      case "all":
      default:
        if (bid == null) {
          result = bookInstanceFacade.getAllBookInstances();
        }
        else {
          result = bookInstanceFacade.getAllCopies(bid);
        }
        break;
    }
    result.sort(Comparator.comparing(BookInstanceDTO::getId));
    model.addAttribute("bookinstances", result);
    model.addAttribute("bookId", bid);
    return "bookinstance/list";
  }

  /**
   * Provides a way to modify a book instance state
   * @param id id of a book instance
   */
  @RequestMapping(value = "/{id}/edit/state", method = RequestMethod.GET)
  public String newBookState(@PathVariable Long id,
                             Model model,
                             UriComponentsBuilder uriComponentsBuilder) {
    model.addAttribute("id", id);
    log.debug("edit_state({id})", id);

    model.addAttribute("bookInstanceNewState", new BookInstanceNewStateDTO());
    model.addAttribute("oldState", bookInstanceFacade.findById(id).getBookState());
    return "/bookinstance/newState";
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
    try {
      bookInstanceFacade.deleteBookInstance(id);
      redirectAttributes
          .addFlashAttribute("alert_success", "Book instance has been successfully deleted.");
    } catch (YellowServiceException yse) {
        redirectAttributes.addFlashAttribute("alert_danger", yse.getMessage());
        log.trace("Attempt to delete lent book instance.");
    }
    return "redirect:" + uriComponentsBuilder.path("/bookinstance")
        .queryParam("bid", bookInstanceDTO.getBook().getId())
        .toUriString();
  }

  /**
   * Changes the availability of the book instance
   * @param id ID of a book instance to delete
   * @param availability {@link BookInstanceAvailability} desired availability
   */
  @RequestMapping(value = "{id}/edit/availability/{availability}", method = RequestMethod.POST)
  public String setState(@PathVariable Long id,
                         @PathVariable String availability,
                         UriComponentsBuilder uriComponentsBuilder,
                         RedirectAttributes redirectAttributes) {
    Long bookId = bookInstanceFacade.findById(id).getBook().getId();
    try {
      BookInstanceAvailability availabilityEnum = BookInstanceAvailability.valueOf(availability);
      bookInstanceFacade.changeBookAvailability(id, availabilityEnum);
      redirectAttributes.addFlashAttribute("alert_success",
          String
              .format("Book instance availability has been successfully set to %s.", availability));
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("alert_warning", "Bad state name");
    }
    return "redirect:" + uriComponentsBuilder
        .path("/book/")
        .pathSegment(bookId.toString())
        .toUriString();
  }

  /**
   * Validates form and changes the state of a book instance
   * @param id id of a book instance to change
   */
  @RequestMapping(value = "/{id}/edit/state", method = RequestMethod.POST)
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
      model.addAttribute("oldState", bookInstanceFacade.findById(id).getBookState());
      return "/bookinstance/newState";
    }
    bookInstanceFacade.changeBookState(formBean);
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(id);
    redirectAttributes.addFlashAttribute("alert_success", "State has been successfully changed.");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance/list")
        .queryParam("bid", bookInstanceDTO.getBook().getId())
        .toUriString();
  }

  /**
   * Validates form and changes the availability of a book instance
   * @param id id of a book instance to change
   */
  @RequestMapping(value = "/{id}/discard", method = RequestMethod.POST)
  public String changeAvailability(@PathVariable Long id,
                                   Model model,
                                   RedirectAttributes redirectAttributes,
                                   UriComponentsBuilder uriComponentsBuilder) {

    log.debug("changeState({id})", id);
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(id);

    if (bookInstanceDTO.getBookAvailability() == BookInstanceAvailability.REMOVED) {
      redirectAttributes.addFlashAttribute("alert_danger", "Book is already discarded.");
    } else if (loanFacade.currentLoanOfBookInstance(bookInstanceDTO) != null) {
      redirectAttributes.addFlashAttribute("alert_danger", "Loan with this book currently exists.");
    } else {
      bookInstanceFacade.changeBookAvailability(id, BookInstanceAvailability.REMOVED);
      bookInstanceDTO = bookInstanceFacade.findById(id);
      redirectAttributes.addFlashAttribute("alert_success", "Book successfully discarded.");
    }
    return "redirect:" + uriComponentsBuilder.path("/bookinstance/list")
        .queryParam("bid", bookInstanceDTO.getBook().getId())
        .toUriString();
  }


  /**
   * Provides form to add new book instance
   * @param bid ID of a book
   */
  @RequestMapping(value = "/new/{bid}", method = RequestMethod.GET)
  public String newBookInstance(@PathVariable long bid, Model model) {
    model.addAttribute("bookId", bid);
    BookDTO bookDTO = bookFacade.getBook(bid);
    log.debug("add()");
    model.addAttribute("bookInstanceCreate", new BookInstanceDTO());
    model.addAttribute("bookName", bookDTO.getName());
    model.addAttribute("bookAuthor", bookDTO.getAuthor());
    return "bookinstance/new";
  }

  /**
   * Validates bean and creates new book instance
   * @param bid ID of a book
   */
  @RequestMapping(value = "/new/{bid}", method = RequestMethod.POST)
  public String create(@PathVariable Long bid,
                       @Valid @ModelAttribute("bookInstanceCreate") BookInstanceCreateDTO formBean,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    log.debug("create(bookInstanceCreate={})", formBean);

    if(bindingResult.hasErrors()) {
      for(ObjectError ge : bindingResult.getGlobalErrors()) {
        log.trace("ObjectError: {}", ge);
      }
      for(FieldError fe : bindingResult.getFieldErrors()) {
        model.addAttribute(fe.getField() + "_error", true);
        log.trace("FieldError: {}", fe);
      }
      model.addAttribute("bookName", bookFacade.getBook(bid).getName());
      model.addAttribute("bookAuthor", bookFacade.getBook(bid).getAuthor());
      model.addAttribute("bookId", bid);
      return "bookinstance/new";
    }
    bookInstanceFacade.createBookInstance(formBean);
    redirectAttributes.addFlashAttribute("alert_success", "New copy of the book has been successfully created");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance")
        .queryParam("bid", bid)
        .toUriString();
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
