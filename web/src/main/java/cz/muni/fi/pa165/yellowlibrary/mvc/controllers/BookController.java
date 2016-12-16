package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.apache.log4j.Logger;
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

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.DepartmentFacade;

/**
 * @author Norbert Slivka
 */
@Controller
@RequestMapping(value = {"/book"})
public class BookController extends CommonController {
  @Inject
  private BookFacade bookFacade;

  @Inject
  private DepartmentFacade departmentFacade;

  @Inject
  private BookInstanceFacade bookInstanceFacade;

  private Logger logger = Logger.getLogger(BookController.class);

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
  public String details(@PathVariable long id, Model model) {
    model.addAttribute("book", bookFacade.getBook(id));
    return "book/details";
  }

  @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
  public String list(Model model) {
    model.addAttribute("books", bookFacade.getAll());
    return "book/list";
  }

  @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
  public String createGet(Model model) {
    model.addAttribute("book", new BookCreateDTO());
    model.addAttribute("departments", departmentFacade.getAll());
    return "book/create";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String create(@Valid @ModelAttribute("book") BookCreateDTO data,
                       BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    logger.debug("BookController#create():POST");

    if (bindingResult.hasErrors()) {
      return "book/create";
    }
    bookFacade.createBook(data);
    redirectAttributes.addFlashAttribute("alert_success",
        String.format("New book %s has been successfully created", data.getName()));
    return "redirect:" + uriComponentsBuilder.path("/book/list").toUriString();
  }

  @RequestMapping(value = {"/{id}/edit"}, method = RequestMethod.GET)
  public String editGet(@PathVariable("id") long id, Model model) {
    BookDTO book = bookFacade.getBook(id);
    BookCreateDTO createDTO = new BookCreateDTO();
    createDTO.setAuthor(book.getAuthor());
    createDTO.setDepartmentId(book.getDepartment().getId());
    createDTO.setDescription(book.getDescription());
    createDTO.setId(book.getId());
    createDTO.setIsbn(book.getIsbn());
    createDTO.setName(book.getName());
    createDTO.setPages(book.getPages());
    //TODO(slivka): bad id handling
    model.addAttribute("book", createDTO);
    model.addAttribute("departments", departmentFacade.getAll());
    return "book/edit";
  }


  @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
  public String editPost(@Valid @ModelAttribute("book") BookCreateDTO data,
                         BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
    logger.debug("BookController#edit():POST");

    if (bindingResult.hasErrors()) {
      return "redirect:" + uriComponentsBuilder.path("/book/{id}/edit")
          .queryParam("id", data.getId()).toUriString();
    }
    BookDTO original = bookFacade.getBook(data.getId());
    data.setAuthor(original.getAuthor());
    data.setName(original.getName());
    data.setIsbn(original.getIsbn());
    bookFacade.updateBook(data);
    redirectAttributes.addFlashAttribute("alert_success",
        String.format("%s has been successfully edited", data.getName()));
    return "redirect:" + uriComponentsBuilder.path("/book/list").toUriString();
  }

  /* HERE STARTS BOOK/{id}/BOOKINSTANCES CONTROLLERS */

  @RequestMapping(value = "/{bid}/bookinstances", method = RequestMethod.GET)
  public String bookList(@PathVariable Long bid, Model model) {
    logger.debug("bookList()");
    return bookList(bid, "all", model);
  }

  @RequestMapping(value = "/{bid}/bookinstances/{filter}", method = RequestMethod.GET)
  public String bookList(@PathVariable Long bid, @PathVariable String filter, Model model) {
    logger.debug("bookList({" + filter + "})");
    switch(filter) {
      case "all":
        model.addAttribute("bookinstances", bookInstanceFacade.getAllCopies(bid));
        break;
      case "available":
        model.addAttribute("bookinstances", bookInstanceFacade.getAllCopiesByAvailability(bid,
            BookInstanceAvailability.AVAILABLE));
        break;
      case "borrowed":
        model.addAttribute("bookinstances", bookInstanceFacade.getAllCopiesByAvailability(bid,
            BookInstanceAvailability.BORROWED));
        break;
      case "removed":
        model.addAttribute("bookinstances", bookInstanceFacade.getAllCopiesByAvailability(bid,
            BookInstanceAvailability.REMOVED));
        break;
    }
    return "bookinstance/list";
  }


  @RequestMapping(value = "/{bid}/bookinstance/new", method = RequestMethod.GET)
  public String newBookInstance(@PathVariable Long bid, Model model) {
    model.addAttribute("bookId", bid);
    logger.debug("add()");
    model.addAttribute("bookInstanceCreate", new BookInstanceDTO());
    model.addAttribute("bookName", bookFacade.getBook(bid).getName());
    model.addAttribute("bookAuthor", bookFacade.getBook(bid).getAuthor());
    return "bookinstance/new";
  }

  @RequestMapping(value = "/{bid}/bookinstance/create", method = RequestMethod.POST)
  public String create(@PathVariable Long bid, @Valid @ModelAttribute("bookInstanceCreate") BookInstanceCreateDTO formBean,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    logger.debug("create(bookInstanceCreate={" + formBean + "})");
    model.addAttribute("bookId", bid);
    if(bindingResult.hasErrors()) {
      for(ObjectError ge : bindingResult.getGlobalErrors()) {
        logger.trace("ObjectError: {" + ge + "}");
      }
      for(FieldError fe : bindingResult.getFieldErrors()) {
        model.addAttribute(fe.getField() + "_error", true);
        logger.trace("FieldError: {" + fe + "}");
      }
      return "bookinstance/new";
    }
    Long id = bookInstanceFacade.createBookInstance(formBean);
    BookInstanceDTO bookInstance= bookInstanceFacade.findById(id);
    redirectAttributes.addFlashAttribute("alert_success", "New book instance of \"" + bookInstance.getBook().getName() +
        "\" has been successfully created");
    return "redirect:" + uriComponentsBuilder.path("/book/{bid}/bookinstances").buildAndExpand(bid).encode().toUriString();
  }

  @ModelAttribute("bookAvailabilities")
  public BookInstanceAvailability[] bookAvailabilities() {
    logger.debug("bookAvailabilities()");
    return BookInstanceAvailability.values();
  }

}
