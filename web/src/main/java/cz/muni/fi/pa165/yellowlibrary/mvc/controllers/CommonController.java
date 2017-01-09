package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Year;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;

/**
 * @author Jozef Zivcic
 */
public abstract class CommonController {

  private final static Logger log = LoggerFactory.getLogger(CommonController.class);

  @Inject
  private UserFacade userFacade;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    log.debug("String trimming binder initialized");
    StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(false);
    binder.registerCustomEditor(String.class, stringTrimmer);
  }

  @ModelAttribute("isAuthenticated")
  public boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null)
      return false;
    return authentication.isAuthenticated() &&
        !(authentication instanceof AnonymousAuthenticationToken);
  }

  @ModelAttribute("getLogin")
  public String getLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null)
      return null;
    return authentication.getName();
  }

  @ModelAttribute("isEmployee")
  public boolean isEmployee() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String role = authentication.getAuthorities().toString();
    return role.equals("[ROLE_EMPLOYEE]");
  }

  @ModelAttribute("isCustomer")
  public boolean isCustomer() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String role = authentication.getAuthorities().toString();
    return role.equals("[ROLE_CUSTOMER]");
  }

  @ModelAttribute("getUserDTO")
  public UserDTO getUserDTO() {
    return userFacade.findByLogin(getLogin());
  }

  @ModelAttribute("getYear")
  public String getYear() {
    return Year.now().toString();
  }
}
