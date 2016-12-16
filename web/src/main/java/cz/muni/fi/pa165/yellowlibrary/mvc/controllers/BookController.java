package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookFacade;
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
}
