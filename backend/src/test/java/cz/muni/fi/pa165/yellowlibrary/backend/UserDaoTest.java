package cz.muni.fi.pa165.yellowlibrary.backend;

import com.google.common.collect.ImmutableList;

import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.UserDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Cokinova
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UserDaoTest  extends AbstractTestNGSpringContextTests {

  @Inject
  private UserDao userDao;

  @PersistenceContext
  private EntityManager em;

  private Book book;

  private BookInstance bookInstance;

  private Loan loan;

  @AfterMethod
  private void tearDown() {
    em.clear();
    for (User user : ImmutableList.copyOf(em.createQuery("SELECT u FROM User u", User.class).getResultList())) {
      em.remove(user);
    }
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserNull(){
      userDao.createUser(null);
  }

  @Test
  public void createUser() {
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setPasswordHash("ABCD");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);

    assertNotNull(user.getId());
    User u = em.find(User.class, user.getId());
    assertEquals(u, user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserNullName() {
    User user = new User();
    user.setName(null);
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setPasswordHash("ABCD");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = DataAccessException.class)
  public void createUserEmptyName() {
    User user = new User();
    user.setName("");
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setPasswordHash("ABCD");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserNullAddress() {
    User user = new User();
    user.setName("James");
    user.setLogin("Jamesik123");
    user.setAddress(null);
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = DataAccessException.class)
  public void createUserEmptyAddress() {
    User user = new User();
    user.setName("James");
    user.setAddress("");
    user.setLogin("Jamesik123");
    user.setPasswordHash("ABCD");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserNullUserType() {
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setUserType(null);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserNullLoans() {
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setUserType(null);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(null);
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserNullNullFines(){
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(null);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void deleteUserNullUser(){
    userDao.deleteUser(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void deleteUserEmptyUser(){
    userDao.deleteUser(new User());
  }

  @Test
  public void deleteUser() {
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setPasswordHash("ABCD");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    em.persist(user);
    userDao.deleteUser(em.find(User.class, user.getId()));

    assertNull(em.find(User.class, user.getId()));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void findByIdNullId(){
    userDao.findById(null);
  }

  @Test
  public void findByIdNonExistingId(){
    assertNull(userDao.findById(123L));
  }

  @Test
  public void findById(){
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setPasswordHash("jamesHash");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    em.persist(user);
    User u = userDao.findById(user.getId());

    assertEquals(u, user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void updateUserNull(){
    userDao.updateUser(null);
  }

  @Test
  public void updateUser(){
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
    user.setLogin("Jamesik123");
    user.setPasswordHash("jamesHash");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    em.persist(user);

    user.setName("Joshua");
    userDao.updateUser(user);

    User u = userDao.findById(user.getId());
    assertEquals(u.getName(), "Joshua");
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void findUserByNullLoginTest() {
    userDao.createUser(null);
  }

  @Test
  public void findUserByLoginTest() {
    User james = new User();
    james.setName("James Good");
    james.setAddress("London");
    james.setLogin("james");
    james.setPasswordHash("jamesHash");
    james.setUserType(UserType.CUSTOMER);
    james.setTotalFines(BigDecimal.ZERO);

    User john = new User();
    john.setName("John Green");
    john.setAddress("London");
    john.setLogin("johnny");
    john.setPasswordHash("johnHash");
    john.setUserType(UserType.EMPLOYEE);
    john.setTotalFines(BigDecimal.ZERO);

    userDao.createUser(james);
    userDao.createUser(john);

    User retUser = userDao.findByLogin("not-existing");
    assertNull(retUser);

    retUser = userDao.findByLogin("james");
    assertEquals(retUser, james);

    retUser = userDao.findByLogin("johnny");
    assertEquals(retUser, john);
  }

  @Test
  public void findAllUsersTest() {
    User james = new User();
    james.setName("James Good");
    james.setAddress("London");
    james.setLogin("james");
    james.setPasswordHash("jamesHash");
    james.setUserType(UserType.CUSTOMER);
    james.setTotalFines(BigDecimal.ZERO);

    User john = new User();
    john.setName("John Green");
    john.setAddress("London");
    john.setLogin("johnny");
    john.setPasswordHash("johnHash");
    john.setUserType(UserType.EMPLOYEE);
    john.setTotalFines(BigDecimal.ZERO);

    List<User> users = userDao.findAllUsers();
    assertTrue(users.isEmpty());

    userDao.createUser(james);
    users = userDao.findAllUsers();
    assertFalse(users.isEmpty());
    assertEquals(users.size(), 1);

    userDao.createUser(john);
    users = userDao.findAllUsers();
    assertFalse(users.isEmpty());
    assertEquals(users.size(), 2);
  }
}
