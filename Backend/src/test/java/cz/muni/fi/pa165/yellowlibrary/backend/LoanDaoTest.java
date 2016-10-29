package cz.muni.fi.pa165.yellowlibrary.backend;

import com.google.common.collect.ImmutableList;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.LoanDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Class for testing Loan
 * @author Norbert Slivka
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class LoanDaoTest extends AbstractTestNGSpringContextTests {

  @Inject
  private LoanDao loanDao;

  @PersistenceContext
  private EntityManager em;

  private User user1;

  private Book book1;

  private BookInstance bookInstance1;

  @BeforeMethod
  public void setUp() {
    user1 = new User();
    user1.setName("Tommy");
    user1.setAddress("Over there");
    user1.setUserType(UserType.CUSTOMER);
    user1.setTotalFines(BigDecimal.ZERO);
    em.persist(user1);

    book1 = new Book();
    book1.setAuthor("Auth");
    book1.setIsbn("1");
    book1.setPages(1);
    book1.setName("Good book");
    book1.setDescription("This is a great book");
    em.persist(book1);

    bookInstance1 = new BookInstance();
    bookInstance1.setBook(book1);
    bookInstance1.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstance1.setVersion("1");
    bookInstance1.setBookState("Fine");
    em.persist(bookInstance1);
  }

  @AfterMethod
  private void tearDown() {
    em.clear();
    for (Loan loan : ImmutableList.copyOf(em.createQuery("SELECT l FROM Loan l", Loan.class).getResultList())) {
      em.remove(loan);
    }
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createNullBookInstanceTest() {
    loanDao.create(null);
  }

  @Test
  public void createLoanAllFilled() {
    Loan original = getDefaultLoan();
    loanDao.create(original);

    Loan value = em.find(Loan.class, original.getId());
    Loan expected = getDefaultLoan();
    assertIsEqual(value, expected);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createLoanNoUser() {
    Loan loan = getDefaultLoan();
    loan.setUser(null);
    loanDao.create(loan);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createLoanNoBook() {
    Loan loan =getDefaultLoan();
    loan.setBook(null);
    loanDao.create(loan);
  }

  @Test
  public void createLoanNotReturned() {
    Loan loan = getDefaultLoan();
    loan.setReturnDate(null);
    loanDao.create(loan);

    Loan expected = getDefaultLoan();
    expected.setReturnDate(null);
    assertIsEqual(em.find(Loan.class, loan.getId()), expected);
  }

  @Test
  public void retrieveLoan() {
    Loan loan = getDefaultLoan();
    em.persist(loan);
    Loan loaded = loanDao.findLoanById(loan.getId());
    assertIsEqual(loan, getDefaultLoan());
  }

  @Test
  public void retrieveNonExistentLoan() {
    assertNull(loanDao.findLoanById(100L));
  }

  @Test
  public void deleteLoan() {
    Loan loan = getDefaultLoan();
    em.persist(loan);

    loanDao.delete(loan);
    assertNull(em.find(Loan.class, loan.getId()));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void deleteNonexistentLoan() {
    Loan loan = getDefaultLoan();
    loanDao.delete(loan);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void deleteDetachedLoan() {
    Loan loan = getDefaultLoan();
    em.persist(loan);
    Loan detached = getDefaultLoan();
    loanDao.delete(detached);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void deleteNullLoan() {
    loanDao.delete(null);
  }

  @Test
  public void updateLoan() {
    Loan original = getDefaultLoan();
    em.persist(original);
    Loan modified = getDefaultLoan();
    modified.setId(original.getId());
    modified.setFine(BigDecimal.TEN);
    loanDao.update(modified);
    Loan value = em.find(Loan.class, modified.getId());
    Loan expected = getDefaultLoan();
    expected.setFine(BigDecimal.TEN);
    assertIsEqual(value, expected);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void updateLoanNull() {
    loanDao.update(null);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void updateLoanNoUser() {
    Loan loan = getDefaultLoan();
    loan.setUser(null);
    loanDao.update(loan);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void updateLoanNoBook() {
    Loan loan = getDefaultLoan();
    loan.setBook(null);
    loanDao.update(loan);
  }

  private Loan getDefaultLoan() {
    Loan loan = new Loan();
    loan.setUser(user1);
    loan.setBook(bookInstance1);
    Date loanDate = new Date(123456);
    Date returnDate = new Date(1234567);
    loan.setDateFrom(loanDate);
    loan.setReturnDate(returnDate);
    loan.setLoanLength(10);
    loan.setLoanState("Fine");
    loan.setFine(BigDecimal.ONE);
    return loan;
  }

  private void assertIsEqual(Loan loan1, Loan loan2) {
    assertEquals(loan1.getBook(), loan2.getBook());
    assertEquals(loan1.getDateFrom(), loan2.getDateFrom());
    assertEquals(loan1.getFine(), loan2.getFine());
    assertEquals(loan1.getLoanLength(), loan2.getLoanLength());
    assertEquals(loan1.getLoanState(), loan2.getLoanState());
    assertEquals(loan1.getReturnDate(), loan2.getReturnDate());
    assertEquals(loan1.getUser(), loan2.getUser());
  }
}
