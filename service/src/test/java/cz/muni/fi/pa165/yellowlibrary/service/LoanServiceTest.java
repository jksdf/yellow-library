package cz.muni.fi.pa165.yellowlibrary.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookInstanceDao;
import cz.muni.fi.pa165.yellowlibrary.backend.dao.LoanDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Norbert Slivka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanServiceTest extends AbstractTestNGSpringContextTests {
  @Mock
  private LoanDao loanDao;

  @Mock
  private BookInstanceDao bookInstanceDao;

  @Inject
  @InjectMocks
  private LoanService loanService;

  private User user1;
  private User user2;
  private Department department;
  private Book book1;
  private Book book2;

  private BookInstance bookInstance1;
  private BookInstance bookInstance2;

  private Loan loan1;
  private Loan loan2;
  private Loan loan3;

  @BeforeTest
  public void setUpUsers() {
    user1 = new User();
    user1.setId(42L);
    user1.setName("Jimmy A.");
    user1.setAddress("Addr");
    user1.setLogin("jimmy");
    user1.setTotalFines(BigDecimal.ZERO);

    user2 = new User();
    user2.setId(43L);
    user2.setName("Peter P.");
    user2.setAddress("Peters addr");
    user2.setLogin("ptr");
    user2.setTotalFines(BigDecimal.TEN);

    department = new Department();
    department.setId(0L);
    department.setName("Department");
    department.setShortName("DEP");

    book1 = new Book();
    book1.setId(21L);
    book1.setName("Effective java");
    book1.setDepartment(department);
    book1.setAuthor("Autor");
    book1.setDescription("Great book");
    book1.setIsbn("1234566");
    book1.setPages(90);

    book2 = new Book();
    book2.setId(22L);
    book2.setName("This one boring book");
    book2.setDepartment(department);
    book2.setAuthor("Autor2");
    book2.setDescription("Not so great book");
    book2.setIsbn("987654321");
    book2.setPages(3);

    department.setBooks(ImmutableList.of(book1, book2));

    bookInstance1 = new BookInstance();
    bookInstance1.setId(123L);
    bookInstance1.setBook(book1);
    bookInstance1.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstance1.setBookState("OK");
    bookInstance1.setVersion("2nd revision");

    bookInstance2 = new BookInstance();
    bookInstance2.setId(456L);
    bookInstance2.setBook(book1);
    bookInstance2.setBookAvailability(BookAvailability.BORROWED);
    bookInstance2.setBookState("Wore down a little");
    bookInstance2.setVersion("original");

    loan1 = new Loan();
    loan1.setId(1L);
    loan1.setUser(user1);
    loan1.setDateFrom(new Date(123));
    loan1.setReturnDate(new Date(234));
    loan1.setBookInstance(bookInstance1);
    loan1.setLoanLength(1);
    loan1.setFine(null);
    loan1.setLoanState("OK");

    loan2 = new Loan();
    loan2.setId(2L);
    loan2.setUser(user1);
    loan2.setDateFrom(new Date(456));
    loan2.setReturnDate(null);
    loan2.setBookInstance(bookInstance2);
    loan2.setLoanLength(23);
    loan2.setFine(null);
    loan2.setLoanState("OK");

    loan3 = new Loan();
    loan3.setId(3L);
    loan3.setUser(user2);
    loan3.setDateFrom(new Date(300));
    loan3.setReturnDate(new Date(111500));
    loan3.setBookInstance(bookInstance1);
    loan3.setLoanLength(1);
    loan3.setFine(BigDecimal.TEN);
    loan3.setLoanState("OK");

    user1.setLoans(ImmutableSet.of(loan1, loan2));
    user2.setLoans(ImmutableSet.of(loan3));
  }

  @BeforeMethod
  public void setUpMocks() {
    MockitoAnnotations.initMocks(this);


    when(loanDao.findLoanById(loan1.getId())).thenReturn(loan1);
    when(loanDao.findLoanById(loan2.getId())).thenReturn(loan2);
    when(loanDao.findLoanById(loan3.getId())).thenReturn(loan3);
    when(loanDao.findAll()).thenReturn(ImmutableList.of(loan1, loan2, loan3));
    when(loanDao.findByBookInstance(bookInstance1)).thenReturn(ImmutableList.of(loan1, loan3));
    when(loanDao.findByBookInstance(bookInstance2)).thenReturn(ImmutableList.of(loan2));
    when(loanDao.findNotReturned()).thenReturn(ImmutableList.of(loan2));
    when(loanDao.findByDate(new Date(123), new Date(234))).thenReturn(ImmutableList.of(loan1));
    when(loanDao.findByBookInstance(null))
        .thenThrow(new NullPointerException("Book instance can not be null"));
    when(loanDao.findLoanById(null))
        .thenThrow(new NullPointerException("ID can not be null"));
    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null) {
        throw new NullPointerException("Argument cannot be null");
      }
      Loan loan = (Loan) arg;
      if (loan.getId() != null) {
        throw new IllegalArgumentException("Loan id must be null");
      }
      loan.setId(89L);
      return loan;
    }).when(loanDao).create(any(Loan.class));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null) {
        throw new NullPointerException("Argument cannot be null");
      }
      Loan loan = (Loan) arg;
      if (loan.getId() == null) {
        throw new NullPointerException("Loan id must be set");
      }
      return loan;
    }).when(loanDao).update(any(Loan.class));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null) {
        throw new NullPointerException("Argument cannot be null");
      }
      Loan loan = (Loan) arg;
      if (loan.getId() == null) {
        throw new NullPointerException("Loan id has to be filled");
      }
      return null;
    }).when(loanDao).delete(any(Loan.class));
  }

  @Test
  public void testCreate() {
    Loan loan = new Loan();
    loan.setUser(user1);
    loan.setDateFrom(new Date(123));
    loan.setReturnDate(new Date(234));
    loan.setBookInstance(bookInstance1);
    loan.setLoanLength(1);
    loan.setFine(null);
    loan.setLoanState("OK");
    loanService.create(loan);
    assertThat(loan.getId()).isNotNull();
    verify(loanDao).create(loan);
    verifyNoMoreInteractions(loanDao);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testCreateNull() {
    loanService.create(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testCreateSetId() {
    bookInstance1.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstanceDao.updateBookInstance(bookInstance1);
    Loan loan = new Loan();
    loan.setId(9L);
    loan.setUser(user1);
    loan.setDateFrom(new Date(123));
    loan.setReturnDate(new Date(234));
    loan.setBookInstance(bookInstance1);
    loan.setLoanLength(1);
    loan.setFine(null);
    loan.setLoanState("OK");
    loanService.create(loan);
  }

  @Test
  public void testUpdate() {
    loan1.setFine(BigDecimal.ONE);
    Loan newLoan = loanService.update(loan1);
    verify(loanDao).update(loan1);
    verifyNoMoreInteractions(loanDao);
    assertThat(loan1.getFine()).isEqualTo(BigDecimal.ONE);
    assertThat(newLoan.getFine()).isEqualTo(BigDecimal.ONE);
    assertThat(loan1.getId()).isEqualTo(newLoan.getId());
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testUpdateNull() {
    loanService.update(null);
  }

  @Test
  public void testFindById() {
    Loan loan = loanService.findById(loan2.getId());
    verify(loanDao).findLoanById(loan2.getId());
    verifyNoMoreInteractions(loanDao);
    assertDeepEquals(loan2, loan);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testFindByIdNull() {
    loanService.findById(null);
  }

  @Test
  public void currentLoanOfBookInstanceOK() {
    Loan loan = loanService.currentLoanOfBookInstance(bookInstance2);
    assertDeepEquals(loan, loan2);
  }

  @Test
  public void currentLoanOfBookInstanceBad() {
    assertThat(loanService.currentLoanOfBookInstance(bookInstance1)).isNull();
  }

  @Test
  public void getAllLoansByBookInstance() {
    List<Loan> loans = loanService.getAllLoansByBookInstance(bookInstance2);
    assertThat(loans).containsExactly(loan2);
    loans = loanService.getAllLoansByBookInstance(bookInstance1);
    assertThat(loans).containsExactly(loan1, loan3);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void getAllLoansByBookInstanceNull() {
    loanService.getAllLoansByBookInstance(null);
  }

  @Test
  public void getNotReturnedLoans() {
    List<Loan> loans = loanService.getNotReturnedLoans();
    assertThat(loans).containsExactly(loan2);
  }

  @Test
  public void getLoansByDateOK() {
    assertThat(loanService.getLoansByDate(new Date(123), new Date(234))).containsExactly(loan1);
  }

  @Test
  public void getLoansByDateEmpty() {
    assertThat(loanService.getLoansByDate(new Date(999998), new Date(999999))).isEmpty();
  }

  @Test
  public void calculateFines() {
    Loan closed = new Loan();
    closed.setId(0L);
    closed.setUser(user1);
    closed.setDateFrom(new Date(123));
    closed.setReturnDate(new Date(234));
    closed.setBookInstance(bookInstance1);
    closed.setLoanLength(1);
    closed.setFine(null);
    closed.setLoanState("OK");

    Loan ok = new Loan();
    ok.setId(1L);
    ok.setUser(user1);
    ok.setDateFrom(new Date(123));
    ok.setReturnDate(new Date(234));
    ok.setBookInstance(bookInstance1);
    ok.setLoanLength(2);
    ok.setFine(null);
    ok.setLoanState("OK");

    Loan bad = new Loan();
    bad.setId(2L);
    bad.setUser(user1);
    bad.setDateFrom(new Date(456));
    bad.setReturnDate(null);
    bad.setBookInstance(bookInstance2);
    bad.setLoanLength(1);
    bad.setFine(null);
    bad.setLoanState("OK");

    final List<Loan> loans = new ArrayList<>();
    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null) {
        throw new NullPointerException("Argument cannot be null");
      }
      Loan loan = (Loan) arg;
      if (loan.getId() == null) {
        throw new NullPointerException("Loan id must be set");
      }
      loans.add(loan);
      return loan;
    }).when(loanDao).update(any(Loan.class));

    when(loanDao.findAll()).thenReturn(ImmutableList.of(closed, ok, bad));

    Calendar c = Calendar.getInstance();
    c.setTime(new Date(10_000_000_000L));

    loanService.calculateFines(c);
    assertThat(loans).containsExactly(bad);
    assertThat(loans.get(0).getFine()).isEqualTo(BigDecimal.valueOf(100L));
  }


  private void assertDeepEquals(Loan loan1, Loan loan2) {
    assertThat(loan1.getId()).isEqualTo(loan2.getId());
    assertThat(loan1.getFine()).isEqualTo(loan2.getFine());
    assertThat(loan1.getBookInstance()).isEqualTo(loan2.getBookInstance());
    assertThat(loan1.getDateFrom()).isEqualTo(loan2.getDateFrom());
    assertThat(loan1.getReturnDate()).isEqualTo(loan2.getReturnDate());
    assertThat(loan1.getLoanLength()).isEqualTo(loan2.getLoanLength());
    assertThat(loan1.getUser()).isEqualTo(loan2.getUser());
  }
}
