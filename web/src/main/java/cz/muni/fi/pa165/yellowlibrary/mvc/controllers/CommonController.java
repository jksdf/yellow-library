package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Jozef Zivcic
 */
public abstract class CommonController {

  @ModelAttribute("isAuthenticated")
  public boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null)
      return false;
    return authentication.isAuthenticated() &&
        !(authentication instanceof AnonymousAuthenticationToken);
  }
}
