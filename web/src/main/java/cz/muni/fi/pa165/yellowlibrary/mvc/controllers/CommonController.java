package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Jozef Zivcic
 */
public abstract class CommonController {

  private final static Logger log = LoggerFactory.getLogger(CommonController.class);

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
    String name = authentication.getName();
    return name;
  }
}
