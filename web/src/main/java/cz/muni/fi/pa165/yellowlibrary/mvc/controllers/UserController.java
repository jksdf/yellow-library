package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;
import cz.muni.fi.pa165.yellowlibrary.mvc.exceptions.ResourceNotFoundException;

/**
 * @author Jozef Zivcic
 */
@Controller
@RequestMapping("/user")
public class UserController extends CommonController {

  private final static Logger log = LoggerFactory.getLogger(UserController.class);

  @Inject
  private UserFacade userFacade;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String userInfo(@PathVariable long id, Model model) {
    log.info("UserController.userInfo()");
    UserDTO userDTO = userFacade.findById(id);
    if (userDTO == null)
      throw new ResourceNotFoundException();
    model.addAttribute("user", userDTO);
    return "user/user";
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String ownUserInfo(Model model) {
    log.info("UserController.ownUserInfo()");
    UserDTO userDTO = getUserDTO();
    model.addAttribute("user", userDTO);
    return "user/user";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String listUsers(Model model) {
    log.info("UserController.listUsers()");
    List<UserDTO> users = userFacade.findAllUsers();
    model.addAttribute("users", users);
    return "user/userlist";
  }
}
