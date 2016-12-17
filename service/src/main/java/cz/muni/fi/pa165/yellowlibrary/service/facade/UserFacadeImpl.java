package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.UserService;

/**
 * @author Jozef Zivcic
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

  @Inject
  private UserService userService;

  @Inject
  private BeanMappingService mappingService;

  @Override
  public void registerNewUser(UserDTO userDTO, String plainTextPassword) {
    if (userDTO == null)
      throw new NullPointerException("userDTO cannot be null");
    User user = mappingService.mapTo(userDTO, User.class);
    userService.create(user, plainTextPassword);
    userDTO.setId(user.getId());
  }

  @Override
  public boolean authenticateUser(UserAuthenticateDTO userAuthenticateDTO,
                                  String plainTextPassword) {
    if (userAuthenticateDTO == null)
      throw new NullPointerException("userAuthenticateDTO must not be null");
    User u = userService.findByLogin(userAuthenticateDTO.getLogin());
    if (u == null)
      throw new IllegalArgumentException("User with given login does not exist");
    return userService.authenticate(u, plainTextPassword);
  }

  @Override
  public void updateUser(UserDTO user) {
    if (user == null)
      throw new NullPointerException("user cannot be null");
    User u = mappingService.mapTo(user, User.class);
    userService.update(u);
  }

  @Override
  public UserDTO findById(Long id) {
    User user = userService.findById(id);
    if (user == null)
      return null;
    return mappingService.mapTo(user, UserDTO.class);
  }

  @Override
  public UserDTO findByLogin(String login) {
    User user = userService.findByLogin(login);
    if (user == null)
      return null;
    return mappingService.mapTo(user, UserDTO.class);
  }

  @Override
  public List<UserDTO> findAllUsers() {
    List<User> users = userService.findAllUsers();
    return mappingService.mapTo(users, UserDTO.class);
  }

  @Override
  public boolean isEmployee(UserDTO userDTO) {
    if (userDTO == null)
      throw new NullPointerException("userDTO cannot be null");
    User user = mappingService.mapTo(userDTO, User.class);
    return userService.isEmployee(user);
  }

  @Override
  public boolean isCustomer(UserDTO userDTO) {
    if (userDTO == null)
      throw new NullPointerException("userDTO cannot be null");
    User user = mappingService.mapTo(userDTO, User.class);
    return userService.isCustomer(user);
  }

  @Override
  public List<UserDTO> findAllUsersWithName(String name) {
    List<User> users = userService.findAllUsersWithName(name);
    return mappingService.mapTo(users, UserDTO.class);
  }
}
