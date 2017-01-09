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
 * Abstract class controller, which is the basis for all controllers.
 *
 * @author Jozef Zivcic
 */
public abstract class CommonController {

  private final static Logger log = LoggerFactory.getLogger(CommonController.class);

  @Inject
  private UserFacade userFacade;

  /**
   * Initialize String trimming binder.
   *
   * @param binder WebDataBinder.
   */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    log.debug("String trimming binder initialized");
    StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(false);
    binder.registerCustomEditor(String.class, stringTrimmer);
  }

  /**
   * Method which checks if user is authenticated. Can be used in JSP.
   *
   * @return True if user, who created request is authenticated, false otherwise.
   */
  @ModelAttribute("isAuthenticated")
  public boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null)
      return false;
    return authentication.isAuthenticated() &&
        !(authentication instanceof AnonymousAuthenticationToken);
  }

  /**
   * Returns login of user, who generated HTTP request.
   *
   * @return Login of user or null if user is not authenticated.
   */
  @ModelAttribute("getLogin")
  public String getLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null)
      return null;
    return authentication.getName();
  }

  /**
   * Checks if user who generated request has a role of EMPLOYEE.
   *
   * @return True if user is EMPLOYEE, false otherwise.
   */
  @ModelAttribute("isEmployee")
  public boolean isEmployee() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String role = authentication.getAuthorities().toString();
    return role.equals("[ROLE_EMPLOYEE]");
  }

  /**
   * Checks if user who generated request has a role of CUSTOMER.
   *
   * @return True if user is CUSTOMER, false otherwise.
   */
  @ModelAttribute("isCustomer")
  public boolean isCustomer() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String role = authentication.getAuthorities().toString();
    return role.equals("[ROLE_CUSTOMER]");
  }

  /**
   * Returns UserDTO object for authenticated user. Identity of user is determined by his login.
   *
   * @return UserDTO objects.
   */
  @ModelAttribute("getUserDTO")
  public UserDTO getUserDTO() {
    return userFacade.findByLogin(getLogin());
  }

  /**
   * @return Current year.
   */
  @ModelAttribute("getYear")
  public String getYear() {
    return Year.now().toString();
  }
}
