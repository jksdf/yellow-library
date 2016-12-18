package cz.muni.fi.pa165.yellowlibrary.service;

import org.springframework.stereotype.Service;

import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;

/**
 * @author Jozef Zivcic
 */
@Service
public interface UserService {

  /**
   * Creates new entity user.
   * @param user User which is created.
   * @param plainTextPassword Password, which is going to be hashed.
   * @throws NullPointerException
   */
  void create(User user, String plainTextPassword);

  /**
   * Authenticates user - controls given plain text password if after applying hash function to it
   * matches user's password hash.
   * @param user User to be authenticated.
   * @param plainTextPassword Password that should match user's hash.
   * @return True, if hashed plain text password matches with user's hash, false otherwise.
   */
  boolean authenticate(User user, String plainTextPassword);

  /**
   * Updates user entity
   * @param user User, which is to be updated.
   */
  void update(User user);

  /**
   * Removes user from storage.
   * @param user User entity, which is removed.
   */
  void delete(User user);

  /**
   * Finds user with identificator id.
   * @param id Id for which user is searched.
   * @return User entity with id as parameter.
   */
  User findById(Long id);

  /**
   * Finds user according to specified login.
   * @param login Searching key for finding specific user.
   * @return User entity, which login corresponds to the one specified as parameter.
   */
  User findByLogin(String login);

  /**
   * Answers if user is employee.
   * @param user User to be controlled.
   * @return True if user is employee, false otherwise.
   */
  boolean isEmployee(User user);

  /**
   * Answers if user is customer.
   * @param user User to be controlled.
   * @return True if user is customer, false otherwise.
   */
  boolean isCustomer(User user);

  /**
   * Finds all users in system.
   * @return List of all users, that are present in system.
   */
  List<User> findAllUsers();

  /**
   * Searches for all users, whose substrings of names match parameter name. This method searches
   * case insensitive.
   * @param name Substring to be searched.
   * @return List of users, whose name matches with substring given as parameter.
   */
  List<User> findAllUsersWithName(String name);
}
