package cz.muni.fi.pa165.yellowlibrary.backend.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;


/**
 * This class implements UserDao interface.
 * @author Jozef Zivcic
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public void createUser(User user) {
    checkUser(user);
    em.persist(user);
  }

  @Override
  public void deleteUser(User user) {
    if (user == null)
      throw new NullPointerException("User cannot be null");
    if (user.getId() == null)
      throw new NullPointerException("User ID cannot be null");
    em.remove(findById(user.getId()));
  }

  @Override
  public User findById(Long id) {
    if (id == null)
      throw new NullPointerException("id cannot be null");
    return em.find(User.class, id);
  }

  @Override
  public void updateUser(User user) {
    checkUser(user);
    em.merge(user);
  }

  /**
   * This method controls user, if it's attributes are valid. In case any attribute is null,
   * throws NullPointerException. In case that login, name or address are empty strings, throws
   * IllegalArgumentException.
   * @param u User to be checked.
   */
  private void checkUser(User u) {
    if (u == null)
      throw new NullPointerException("User is null");
    if (u.getLogin() == null)
      throw new NullPointerException("User login is null");
    if (u.getName() == null)
      throw new NullPointerException("User name cannot be null");
    if (u.getAddress() == null)
      throw new NullPointerException("User address cannot be null");
    if (u.getTotalFines() == null)
      throw new NullPointerException("User total fines is null");
    if (u.getLoans() == null)
      throw new NullPointerException("User loans is null");
    if (u.getUserType() == null)
      throw new NullPointerException("User type cannot be null");
    checkEmptyString(u.getLogin(), "Login");
    checkEmptyString(u.getName(), "Name");
    checkEmptyString(u.getAddress(), "Address");
  }

  /**
   * Checks if given string is not empty. If is, then throws IllegalArgumentException.
   * @param controlled String that must not be empty.
   * @param name Name of string, that is controlled (Used only as additional info in exception
   *             string).
   */
  private void checkEmptyString(String controlled, String name) {
    if (controlled.trim().isEmpty())
      throw new IllegalArgumentException(name + " string is empty");
  }
}
