package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookSearchDTO;
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

  @InitBinder
  public void initISBNBinder(WebDataBinder dataBinder) {
    logger.debug("ISBN trimmer binding initialized");
    dataBinder.registerCustomEditor(String.class, "isbn",
        new StringTrimmerEditor("-", false));
  }

  @InitBinder
  public void initSearchDepartmentsBinder(WebDataBinder binder) {
    binder.registerCustomEditor(Set.class, "departmentIds", new CustomCollectionEditor(Set.class) {
      @Override
      protected Object convertElement(Object element) {
        if (element instanceof String && !element.equals("")) {
          try {
            return Long.parseLong((String) element);
          } catch (NumberFormatException e) {
            logger.debug("Bad format", e);
            return null;
          }
        } else if (element instanceof Long) {
          return element;
        }
        return null;
      }
    });
  }

  @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
  public String details(@PathVariable long id,
                        @RequestParam(required = false, defaultValue = "ALL",
                            value = "availability") String type,
                        Model model) {
    model.addAttribute("book", bookFacade.getBook(id));
    List<BookInstanceDTO> books;
    if (type.equals("ALL")) {
      books = bookInstanceFacade.getAllCopies(id);
    } else {
      books =
          bookInstanceFacade.getAllCopiesByAvailability(id, BookInstanceAvailability.valueOf(type));
    }
    model.addAttribute("instances", books);
    model.addAttribute("instanceRemoved", BookInstanceAvailability.REMOVED);
    return "book/details";
  }

  @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
  public String list(Model model, @Valid @ModelAttribute("bookSearch") BookSearchDTO search) {
    if (search.getDepartmentIds() != null) {
      search.getDepartmentIds().remove(-1L);
    } else {
      search.setDepartmentIds(ImmutableSet.of());
    }
    if (Strings.isNullOrEmpty(search.getName()) &&
        Strings.isNullOrEmpty(search.getAuthor()) &&
        Strings.isNullOrEmpty(search.getDescription()) &&
        Strings.isNullOrEmpty(search.getIsbn()) &&
        search.getDepartmentIds() != null &&
        search.getDepartmentIds().isEmpty()) {
      model.addAttribute("books", bookFacade.getAll());
    } else {
      model.addAttribute("books", bookFacade.findBooks(search));
    }
    model.addAttribute("departments", departmentFacade.getAll());
    return "/book/list";
  }

  @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
  public String createGet(Model model) {
    model.addAttribute("book", new BookCreateDTO());
    model.addAttribute("departments", departmentFacade.getAll());
    return "/book/create";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String create(@Valid @ModelAttribute("book") BookCreateDTO data,
                       BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    logger.debug("BookController#create():POST");

    if (bindingResult.hasErrors()) {
      model.addAttribute("departments", departmentFacade.getAll());
      return "/book/create";
    }
    bookFacade.createBook(data);
    redirectAttributes.addFlashAttribute("alert_success",
        String.format("New book %s has been successfully created", data.getName()));
    return "redirect:" + uriComponentsBuilder.path("/book/list").toUriString();
  }

  @RequestMapping(value = {"/{id}/edit"}, method = RequestMethod.GET)
  public String editGet(@PathVariable("id") long id, Model model) {
    logger.debug("BookController#edit():GET");
    BookDTO book = bookFacade.getBook(id);
    BookCreateDTO createDTO = new BookCreateDTO();
    createDTO.setAuthor(book.getAuthor());
    createDTO.setDepartmentId(book.getDepartment().getId());
    createDTO.setDescription(book.getDescription());
    createDTO.setId(book.getId());
    createDTO.setIsbn(book.getIsbn());
    createDTO.setName(book.getName());
    createDTO.setPages(book.getPages());
    model.addAttribute("book", createDTO);
    model.addAttribute("departments", departmentFacade.getAll());
    return "/book/edit";
  }


  @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
  public String editPost(@Valid @ModelAttribute("book") BookCreateDTO data,
                         BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
    logger.debug("BookController#edit():POST");

    if (bindingResult.hasErrors()) {
      return uriComponentsBuilder.path("/book/{id}/edit")
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
}
