package cz.muni.fi.pa165.yellowlibrary.backend.dao;


import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;

/**
 * DAO for entity User
 * @author Jozef Zivcic
 */
public interface UserDao {

  /**
   * Persist new user into Database.
   * @param user User entity which is persisted.
   */
  void createUser(User user);

  /**
   * Deletes specified User from database.
   * @param user User to be deleted.
   */
  void deleteUser(User user);

  /**
   * Finds User entity according to its id.
   * @param id Unique identifier for User entity.
   * @return User whose id conforms to param id.
   */
  User findById(Long id);

  /**
   * Updates persisted user with new user.
   * @param user New User entity, which replaces an old one.
   */
  void updateUser(User user);
}
