package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

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


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String list(Model model) {
    model.addAttribute("loans", loanFacade.getNotReturnedLoans());
    return "loan/list";
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String newBookInstance(Model model) {
    log.debug("new()");
    model.addAttribute("loanCreate", new LoanCreateDTO());
    //model.addAttribute("bookInstancies", bookInstanceFacade.getAllBookInstances());
    return "loan/new";
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

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public String create(@Valid @ModelAttribute("loanCreate") LoanCreateDTO formBean,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriComponentsBuilder) {
    log.debug("create(loanCreate={})", formBean);

    if(bindingResult.hasErrors()) {
      for(ObjectError ge : bindingResult.getGlobalErrors()) {
        log.trace("ObjectError: {}", ge);
      }
      for(FieldError fe : bindingResult.getFieldErrors()) {
        model.addAttribute(fe.getField() + "_error", true);
        log.trace("FieldError: {}", fe);
      }
      return "loan/new";
    }
    Long id = loanFacade.create(formBean);
    LoanDTO loan = loanFacade.findById(id);
    String userName = loan.getUser().getName();
    String bookName = loan.getBookInstance().getBook().getName();
    redirectAttributes.addFlashAttribute("alert_success", "New loan for \""+ userName + "\" of \"" + bookName +
        "\" has been successfully created");
    return "redirect:" + uriComponentsBuilder.path("/bookinstance/list").toUriString();
  }
}
