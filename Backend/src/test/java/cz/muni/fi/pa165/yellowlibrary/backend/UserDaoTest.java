package cz.muni.fi.pa165.yellowlibrary.backend;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.UserDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

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

  @BeforeMethod
  public void setUp() throws Exception {
    book = new Book();
    book.setAuthor("J.K.Rowling");
    book.setIsbn("123-456");
    book.setPages(1);
    book.setName("Harry Potter");
    book.setDescription("Fantasy book");
    em.persist(book);

    bookInstance = new BookInstance();
    bookInstance.setBook(book);
    bookInstance.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstance.setVersion("1.0");
    bookInstance.setBookState("New");
    em.persist(bookInstance);

    loan = new Loan();
    loan.setDateFrom(new Date(123456));
    loan.setBookInstance(bookInstance);
    loan.setLoanState("ok");
    loan.setLoanLength(30);
    loan.setFine(BigDecimal.ZERO);
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
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserEmptyName() {
    User user = new User();
    user.setName("");
    user.setAddress("Smith");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserNullAddress() {
    User user = new User();
    user.setName("James");
    user.setAddress(null);
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserEmptyAddress() {
    User user = new User();
    user.setName("James");
    user.setAddress("");
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
    user.setUserType(null);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createUserNullNullFines(){
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(null);
    user.setLoans(new HashSet<>());
    userDao.createUser(user);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void deleteUserNullUser(){
    userDao.deleteUser(null);
  }
  @Test(expectedExceptions = ConstraintViolationException.class)
  public void deleteUserEmptyUser(){
    userDao.deleteUser(new User());
  }

  @Test
  public void deleteUser() {
    User user = new User();
    user.setName("James");
    user.setAddress("Smith");
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
    user.setUserType(UserType.CUSTOMER);
    user.setTotalFines(BigDecimal.ZERO);
    user.setLoans(new HashSet<>());
    em.persist(user);

    user.setName("Joshua");
    userDao.updateUser(user);

    User u = userDao.findById(user.getId());
    assertEquals(u.getName(), "Joshua");
  }

}