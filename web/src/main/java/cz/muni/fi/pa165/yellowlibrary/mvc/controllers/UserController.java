package cz.muni.fi.pa165.yellowlibrary.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;

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
      return "redirect:/not_found";
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
  public String listUsers(Model model, @RequestParam(value = "user_name",required = false) String userName) {
    log.info("UserController.listUsers()");
    List<UserDTO> users = null;
    if (userName == null)
      users = userFacade.findAllUsers();
    else
      users = userFacade.findAllUsersWithName(userName);
    model.addAttribute("users", users);
    return "user/userlist";
  }

  @RequestMapping(value = "/{id}/update_fines", method = RequestMethod.POST)
  public String updateFines(@PathVariable("id") long id, @RequestParam("amount") String amount) {
    log.info("UserController.updateFines()");
    if (amount.isEmpty())
      return "redirect:/user/" + String.valueOf(id) + "?error=empty";
    BigDecimal number = new BigDecimal(amount);
    if (number.compareTo(BigDecimal.ZERO) <= 0)
      return "redirect:/user/" + String.valueOf(id) + "?error=negative";
    UserDTO updatedUser = userFacade.findById(id);
    BigDecimal newFines = updatedUser.getTotalFines();
    if (number.compareTo(newFines) > 0)
      return "redirect:/user/" + String.valueOf(id) + "?error=subtract";
    newFines = newFines.subtract(number);
    updatedUser.setTotalFines(newFines);
    userFacade.updateUser(updatedUser);
    return "redirect:/user/" + String.valueOf(id);
  }
}
