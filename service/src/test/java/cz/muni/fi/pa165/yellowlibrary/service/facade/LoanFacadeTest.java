package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.LoanFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookInstanceDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.DateService;
import cz.muni.fi.pa165.yellowlibrary.service.DateServiceImpl;
import cz.muni.fi.pa165.yellowlibrary.service.LoanService;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
  * @author Jozef Zivcic
  */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanFacadeTest extends AbstractTestNGSpringContextTests {

  @Mock
  private LoanService loanService;

  @Spy
  @Inject
  private BeanMappingService beanMappingService;

  @InjectMocks
  private LoanFacade loanFacade = new LoanFacadeImpl();

  private Loan loan1;
  private User user1;
  private BookInstance bookInstance1;
  private Department department;
  private Book book1;

  @BeforeMethod
  public void setUp() {
    setupLoan();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createLoanTest() {
    LoanCreateDTO l = beanMappingService.mapTo(loan1, LoanCreateDTO.class);
    loanFacade.create(l);
    verify(loanService).create(any(Loan.class));
  }

  @Test
  public void updateLoanTest() {
    when(loanService.update(any(Loan.class))).thenReturn(loan1);
    LoanDTO l = beanMappingService.mapTo(loan1, LoanDTO.class);
    l.setId(1L);
    loanFacade.update(l);
    verify(loanService).update(any(Loan.class));
  }

  @Test
  public void findById() {
    when(loanService.findById(any(Long.class))).thenReturn(loan1);
    LoanDTO l = beanMappingService.mapTo(loan1, LoanDTO.class);
    loanFacade.findById(1L);
    verify(loanService).findById(1L);
  }

  @Test
  public void currentLoanOfBookInstanceTest() {
    when(loanService.currentLoanOfBookInstance(bookInstance1)).thenReturn(loan1);
    BookInstanceDTO b = beanMappingService.mapTo(bookInstance1, BookInstanceDTO.class);
    loanFacade.currentLoanOfBookInstance(b);
    verify(loanService).currentLoanOfBookInstance(bookInstance1);
  }

  @Test
  public void getAllBookInstanceLoansTest() {
    when(loanService.getAllLoansByBookInstance(bookInstance1)).thenReturn(Arrays.asList(loan1));
    BookInstanceDTO b = beanMappingService.mapTo(bookInstance1, BookInstanceDTO.class);
    loanFacade.getAllBookInstanceLoans(b);
    verify(loanService).getAllLoansByBookInstance(bookInstance1);
  }

  @Test
  public void getNotReturnedLoansTest() {
    when(loanService.getNotReturnedLoans()).thenReturn(Arrays.asList(loan1));
    loanFacade.getNotReturnedLoans();
    verify(loanService).getNotReturnedLoans();
  }

  @Test
  public void getLoansByDateTest() {
    when(loanService.getLoansByDate(new Date(123456), new Date(123457))).
        thenReturn(Arrays.asList(loan1));
    loanFacade.getLoansByDate(new Date(123456), new Date(123457));
    verify(loanService).getLoansByDate(new Date(123456), new Date(123457));
  }

  private void setupLoan() {
    department = new Department();
    department.setName("Art");
    department.setShortName("ART");

    user1 = new User();
    user1.setName("Tommy");
    user1.setAddress("Over there");
    user1.setUserType(UserType.CUSTOMER);
    user1.setTotalFines(BigDecimal.ZERO);
    user1.setLogin("tommy1");
    user1.setPasswordHash("ABCD");

    book1 = new Book();
    book1.setAuthor("Auth");
    book1.setIsbn("1");
    book1.setPages(1);
    book1.setName("Good book");
    book1.setDescription("This is a great book");
    book1.setDepartment(department);

    bookInstance1 = new BookInstance();
    bookInstance1.setBook(book1);
    bookInstance1.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstance1.setVersion("1");
    bookInstance1.setBookState("Fine");

    loan1 = getDefaultLoan();
  }

  private Loan getDefaultLoan() {
    Loan loan = new Loan();
    loan.setUser(user1);
    loan.setBookInstance(bookInstance1);
    Date loanDate = new Date(123456);
    Date returnDate = new Date(1234567);
    loan.setDateFrom(loanDate);
    loan.setReturnDate(returnDate);
    loan.setLoanLength(10);
    loan.setLoanState("Fine");
    loan.setFine(BigDecimal.ONE);
    return loan;
  }
}
