package cz.muni.fi.pa165.yellowlibrary.api.facade;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;

/**
 * @author Jozef Zivcic
 */
public interface UserFacade {

  /**
   * Creates new user in the system.
   * @param userDTO new user.
   * @param plainTextPassword Password of the new user.
   */
  void registerNewUser(UserDTO userDTO, String plainTextPassword);

  /**
   * Authenticates user - controls if given credentials are correct.
   * @param userAuthenticateDTO User to be authenticated.
   * @param plainTextPassword Password that should match user's hash.
   * @return True, if user is authenticated, false otherwise.
   */
  boolean authenticateUser(UserAuthenticateDTO userAuthenticateDTO, String plainTextPassword);

  /**
   * Updates user in the system.
   * @param user User, which is updated
   */
  void updateUser(UserDTO user);

  /**
   * Searches for user with id in system.
   * @param id User id.
   * @return If user with id exists in the system, then returns user, null otherwise.
   */
  UserDTO findById(Long id);

  /**
   * Searches for user with specified email in the system.
   * @param login User login.
   * @return If user with specified login is present in the system, then returns user,
   * null otherwise.
   */
  UserDTO findByLogin(String login);

  /**
   * Finds all users in system.
   * @return List of all users, that are present in the system.
   */
  List<UserDTO> findAllUsers();

  /**
   * Finds out, if user is employee.
   * @param userDTO User which is checked.
   * @return True, if the user is an employee, false otherwise.
   */
  boolean isEmployee(UserDTO userDTO);

  /**
   * Finds out, if user is a customer.
   * @param userDTO User, which is checked.
   * @return True, if the user is a customer, false otherwise.
   */
  boolean isCustomer(UserDTO userDTO);

  /**
   * Searches for all users, whose substrings of names match parameter name. This method searches
   * case insensitive.
   * @param name Substring to be searched.
   * @return List of users, whose name matches with substring given as parameter.
   */
  List<UserDTO> findAllUsersWithName(String name);
}
