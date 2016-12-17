package cz.muni.fi.pa165.yellowlibrary.backend.dao;


import java.util.List;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;

/**
 * DAO for entity User
 * @author Jozef Zivcic
 */
public interface UserDao {

  /**
   * Persist new user into Database.
   * @param user User entity which is persisted.
   * @throws NullPointerException if user, or some of it's attributes are null.
   * @throws IllegalArgumentException if login, name or address are empty strings.
   */
  void createUser(User user);

  /**
   * Deletes specified User from database.
   * @param user User to be deleted.
   * @throws NullPointerException if user or user ID is null.
   */
  void deleteUser(User user);

  /**
   * Finds User entity according to its id.
   * @param id Unique identifier for User entity.
   * @return User whose id conforms to param id.
   * @throws NullPointerException if param id is null.
   */
  User findById(Long id);

  /**
   * Updates persisted user with new user.
   * @param user New User entity, which replaces an old one.
   * @throws NullPointerException if user, or some of it's attributes are null.
   * @throws IllegalArgumentException if login, name or address are empty strings.
   */
  void updateUser(User user);

  /**
   * Finds user according to specified login.
   * @param user User, which attribute login is user as searching key for finding specific user.
   * @return User entity, which login corresponds to the one specified as parameter. If no user
   * is found, then returns null.
   */
  User findByLogin(String login);

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
