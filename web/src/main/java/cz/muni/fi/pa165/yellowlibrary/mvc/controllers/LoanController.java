package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.beans.PropertyEditorSupport;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.exceptions.BookInstanceNotAvailableException;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.LoanFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;

/**
 * @author cokinova
 */
@Controller
@RequestMapping("/loan")
public class LoanController extends CommonController {

  final static Logger log = LoggerFactory.getLogger(LoanController.class);

  @Autowired
  private ApplicationContext context;

  @Inject
  private LoanFacade loanFacade;

  @Inject
  private BookInstanceFacade bookInstanceFacade;

  @Inject
  private UserFacade userFacade;

  @InitBinder
  protected void initBinder(WebDataBinder binder, Locale locale) {
    String dateFormatString = this.context.getMessage("date_format", null, locale);
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    binder.registerCustomEditor(UserDTO.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        try {
          UserDTO user = userFacade.findById(Long.parseLong(text));
          setValue(user);
        }
        catch (Exception ex) {}
      }
    });

    binder.registerCustomEditor(BookInstanceDTO.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        try {
          BookInstanceDTO bookInstance = bookInstanceFacade.findById(Long.parseLong(text));
          setValue(bookInstance);
        }
        catch (Exception ex) {}
      }
    });
  }

  @RequestMapping(value = {"", "/","/list"}, method = RequestMethod.GET)
  public String list(Model model) {
    if (isEmployee()){
      model.addAttribute("loans", loanFacade.getAllLoans());
    } else {
      model.addAttribute("loans", loanFacade.getLoansByUser(getUserDTO()));
    }
    return "loan/list";
  }

  @RequestMapping(value = "/recalculateFines", method = RequestMethod.GET)
  public String recalculateFines(Model model, RedirectAttributes ra) {
    loanFacade.CalculateFinesForExpiredLoans();
    return "redirect:/loan/list";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String view(@PathVariable Long id, Model model) {
    log.trace("view({})", id);
    model.addAttribute("loan", loanFacade.findById(id));
    return "loan/view";
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String newBookInstance(Model model) {
    log.trace("newBookInstance()[GET]");
    model.addAttribute("loan", new LoanCreateDTO());
    return "loan/new";
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String newBookInstancePost(@Valid @ModelAttribute("loan") LoanCreateDTO formData,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder, Locale locale) {
    log.trace("newBookInstancePost()[POST]: {}", formData);
    if (bindingResult.hasErrors()) {
      return "loan/new";
    }

    Calendar now = Calendar.getInstance();
    formData.setDateFrom(now.getTime());
    try {
      loanFacade.create(formData);
    } catch (BookInstanceNotAvailableException ex) {
      bindingResult.addError(new ObjectError("bookInstance",
          context.getMessage("loan.new.bookInstanceNotAvailable", null, locale)));
      return "loan/new";
    }

    String userName = formData.getUser().getName();
    String bookName = formData.getBookInstance().getBook().getName();
    redirectAttributes.addFlashAttribute("alert_success",
            MessageFormat.format(context.getMessage("loan.new.success", null, locale), userName, bookName));

    return "redirect:" + uriComponentsBuilder.path("/loan/list").toUriString();
  }

  @RequestMapping(value = {"/{id}/edit"}, method = RequestMethod.GET)
  public String editGet(@PathVariable("id") long id, Model model) {
    log.trace("editGet()[GET]");
    LoanDTO loan = loanFacade.findById(id);
    model.addAttribute("loan", loan);
    return "loan/edit";
  }

  @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
  public String editPost(@Valid @ModelAttribute("loan") LoanDTO data,
                         BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder, Locale locale) {
    log.trace("editPost()[POST]");
    if(bindingResult.hasErrors()) {
      return "/loan/edit";
    }

    loanFacade.update(data);
    redirectAttributes.addFlashAttribute("alert_success",
            context.getMessage("loan.edit.success", null, locale));
    return "redirect:" + uriComponentsBuilder.path("/loan/list").toUriString();
  }

  @ModelAttribute("bookInstancies")
  public List<BookInstanceDTO> bookInstancies() {
    log.trace("bookInstancies()");
    return bookInstanceFacade.getAllBookInstances();
  }

  @ModelAttribute("users")
  public List<UserDTO> users() {
    log.trace("users()");
    return userFacade.findAllUsers();
  }

}