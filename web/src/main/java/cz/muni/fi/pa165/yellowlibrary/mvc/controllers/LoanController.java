package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.facade.LoanFacade;
import cz.muni.fi.pa165.yellowlibrary.service.BookInstanceService;

/**
 * @author cokinova
 */
@Controller
@RequestMapping("/loan")
public class LoanController extends CommonController{

  @Inject
  private LoanFacade loanFacade;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String list(Model model) {
    model.addAttribute("loans", loanFacade.getNotReturnedLoans());
    return "loan/list";
  }
}
