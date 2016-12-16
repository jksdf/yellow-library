package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.LoanFacade;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;

/**
 * @author cokinova
 */
@Controller
@RequestMapping("/loan")
public class LoanController extends CommonController{

  final static Logger log = LoggerFactory.getLogger(LoanController.class);

  @Inject
  private LoanFacade loanFacade;

  @Inject
  private BookInstanceFacade bookInstanceFacade;

  @Inject
  private UserFacade userFacade;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy");
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
  public String recalculateFines(Model model, UriComponentsBuilder uriComponentsBuilder) {
    loanFacade.CalculateFinesForExpiredLoans();
    return "redirect:" + uriComponentsBuilder.path("/loan/list").toUriString();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String view(@PathVariable Long id, Model model) {
    log.debug("details({})", id);
    model.addAttribute("loan", loanFacade.findById(id));
    return "loan/details";
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String newBookInstance(Model model) {
    log.debug("new()");
    model.addAttribute("loanCreate", new LoanCreateDTO());
    //model.addAttribute("bookInstancies", bookInstanceFacade.getAllBookInstances());
    return "loan/new";
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String newBookInstancePost(@Valid @ModelAttribute("loanCreate") LoanCreateDTO formData,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    log.debug("create(new={})", formData);

    if (bindingResult.hasErrors()) {
      for (ObjectError ge : bindingResult.getGlobalErrors()) {
        log.trace("ObjectError: {}", ge);
      }
      for (FieldError fe : bindingResult.getFieldErrors()) {
        model.addAttribute(fe.getField() + "_error", true);
        log.trace("FieldError: {}", fe);
      }
    }

    Calendar now = Calendar.getInstance();
    formData.setDateFrom(now.getTime());
    Long id = loanFacade.create(formData);

    String userName = formData.getUser().getName();
    String bookName = formData.getBookInstance().getBook().getName();
    redirectAttributes.addFlashAttribute("alert_success", "New loan for \""+ userName + "\" of \"" + bookName +
        "\" has been successfully created");

    return "redirect:" + uriComponentsBuilder.path("/loan/list").toUriString();
  }

  @RequestMapping(value = {"/{id}/edit"}, method = RequestMethod.GET)
  public String editGet(@PathVariable("id") long id, Model model) {
    LoanDTO loan = loanFacade.findById(id);
    LoanCreateDTO loanCreateDTO = new LoanCreateDTO();
    loanCreateDTO.setDateFrom(loan.getDateFrom());
    loanCreateDTO.setBookInstance(loan.getBookInstance());
    loanCreateDTO.setLoanLength(loan.getLoanLength());
    loanCreateDTO.setLoanState(loan.getLoanState());
    loanCreateDTO.setReturnDate(loan.getReturnDate());
    model.addAttribute("loan", loan);
    return "loan/edit";
  }


  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public String editPost(@Valid @ModelAttribute("loan") LoanDTO data,
                         BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriComponentsBuilder) {
    log.debug("Loan edit():POST");

    /*if (bindingResult.hasErrors()) {
      return "redirect:" + uriComponentsBuilder.path("/loan/{id}/edit")
          .queryParam("id", data.getId()).toUriString();
    }*/
    if(bindingResult.hasErrors()) {
      for(ObjectError ge : bindingResult.getGlobalErrors()) {
        log.trace("ObjectError: {}", ge);
      }
      for(FieldError fe : bindingResult.getFieldErrors()) {
        model.addAttribute(fe.getField() + "_error", true);
        log.trace("FieldError: {}", fe);
      }
      return uriComponentsBuilder.path("/loan/{id}/edit")
          .queryParam("id", data.getId()).toUriString();
    }
    loanFacade.update(data);
    redirectAttributes.addFlashAttribute("alert_success",
        String.format("Loan has been successfully edited"));
    return "redirect:" + uriComponentsBuilder.path("/loan/list").toUriString();
  }

  @ModelAttribute("bookInstancies")
  public List<BookInstanceDTO> bookInstancies() {
    log.debug("bookInstancies()");
    return bookInstanceFacade.getAllBookInstances();
  }

  @ModelAttribute("users")
  public List<UserDTO> users() {
    log.debug("users()");
    return userFacade.findAllUsers();
  }


}
