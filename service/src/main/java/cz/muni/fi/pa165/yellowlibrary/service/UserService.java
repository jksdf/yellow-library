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
   * @throws NullPointerException
   */
  void create(User user);

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
}
