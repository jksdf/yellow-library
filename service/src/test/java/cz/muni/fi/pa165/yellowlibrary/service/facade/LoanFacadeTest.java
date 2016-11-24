package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

/**
 * @author Norbert Slivka
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanFacadeTest extends AbstractTestNGSpringContextTests {
/*
  @Mock
  private LoanService loanService;

  @Spy
  @Inject
  private BeanMappingService mappingService;

  @InjectMocks
  private LoanFacade loanFacade = new LoanFacadeImpl();

  private Loan loan1;
  private User user1;
  private Department department;
  private Book book1;
  private BookInstance bookInstance1;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    user1 = new User();
    user1.setId(42L);
    user1.setName("Jimmy A.");
    user1.setAddress("Addr");
    user1.setLogin("jimmy");
    user1.setTotalFines(BigDecimal.ZERO);

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

    department.setBooks(ImmutableList.of(book1));

    bookInstance1 = new BookInstance();
    bookInstance1.setId(123L);
    bookInstance1.setBook(book1);
    bookInstance1.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstance1.setBookState("OK");
    bookInstance1.setVersion("2nd revision");

    loan1 = new Loan();
    loan1.setId(1L);
    loan1.setUser(user1);
    loan1.setDateFrom(new Date(123));
    loan1.setReturnDate(null);
    loan1.setBookInstance(bookInstance1);
    loan1.setLoanLength(1);
    loan1.setFine(null);
    loan1.setLoanState("OK");

    user1.setLoans(ImmutableSet.of(loan1));

    when(loanService.findById(loan1.getId())).thenReturn(loan1);
    when(loanService.currentLoanOfBookInstance(bookInstance1)).thenReturn(loan1);
    when(loanService.getAllLoansByBookInstance(bookInstance1)).thenReturn(ImmutableList.of(loan1));
    when(loanService.getNotReturnedLoans()).thenReturn(ImmutableList.of(loan1));

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
    }).when(loanService).create(any(Loan.class));

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
    }).when(loanService).update(any(Loan.class));
  }

  @Test
  public void createLoan() {
    LoanDTO loan = new LoanDTO();
    loan.setUser(user1);
    loan.setDateFrom(new Date(123));
    loan.setReturnDate(null);
    loan.setBookInstance(bookInstance1);
    loan.setLoanLength(1);
    loan.setFine(null);
    loan.setLoanState("OK");
    loanFacade.create(loan);
    assertThat(loan.getId()).isNotNull();
    assertThat(loan).isNotEqualTo(loan1);
  }

  @Test
  public void updateLoan() {
    loan1.setFine(BigDecimal.ONE);
    loanService.update(loan1);
    assertThat(loan1.getId()).isNotNull();
    assertThat(loan1.getFine()).isEqualTo(BigDecimal.ONE);
  }

  @Test
  public void findById() {
    LoanDTO ret = loanFacade.findById(loan1.getId());

  }

  private void assertDeepEquals(LoanDTO loan1, Loan loan2) {
    assertThat(loan1.getId()).isEqualTo(loan2.getId());
    assertThat(loan1.getFine()).isEqualTo(loan2.getFine());
    assertThat(loan1.getBookInstance()).isEqualTo(loan2.getBookInstance());
    assertThat(loan1.getDateFrom()).isEqualTo(loan2.getDateFrom());
    assertThat(loan1.getReturnDate()).isEqualTo(loan2.getReturnDate());
    assertThat(loan1.getLoanLength()).isEqualTo(loan2.getLoanLength());
    assertThat(loan1.getUser()).isEqualTo(loan2.getUser());
  }*/
}
