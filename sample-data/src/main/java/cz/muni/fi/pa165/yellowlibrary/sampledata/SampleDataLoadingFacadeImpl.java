package cz.muni.fi.pa165.yellowlibrary.sampledata;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;
import cz.muni.fi.pa165.yellowlibrary.service.BookInstanceService;
import cz.muni.fi.pa165.yellowlibrary.service.BookService;
import cz.muni.fi.pa165.yellowlibrary.service.DepartmentService;
import cz.muni.fi.pa165.yellowlibrary.service.LoanService;
import cz.muni.fi.pa165.yellowlibrary.service.UserService;

/**
 * @author Jozef Zivcic
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

  final static Logger log = Logger.getLogger(SampleDataLoadingFacadeImpl.class);


  @Inject
  private BookInstanceService bookInstanceService;

  @Inject
  private BookService bookService;

  @Inject
  private DepartmentService departmentService;

  @Inject
  private UserService userService;

  @Inject
  private LoanService loanService;

  @Override
  @SuppressWarnings("unused")
  public void loadData() {
    Department dep1 = department("ART", "Agriculture, Robots and Tennis");
    Department dep2 = department("BIO", "Biannual Industry Obituaries");
    Department dep3 = department("ROM", "Romance");

    log.debug("Loaded departments.");

    Book book1 = book(100, "Technological Advancements on Farms", "9781482251074", "Joe Backhand",
        "Short description of robotic advancements on local farms.", dep1);
    Book book2 = book(20, "How to play tennis with wheat straws", "9780747560722", "Joe Backhand",
        "Brief overview on how to make tennic racket using only wheat straws.", dep1);
    Book book3 = book(123, "Safecorp Industries Obituaries", "9788478885626", "Marcus Coffin",
        "Safecorp Industries Obituaries 1966", dep2);

    log.debug("Loaded books.");

    BookInstance bookInstance1 = bookInstance(book1, BookAvailability.AVAILABLE, "Page 4 torn out",
        "2nd Edition");
    BookInstance bookInstance2 = bookInstance(book1, BookAvailability.AVAILABLE, "Cover damaged",
        "2nd Edition");
    BookInstance bookInstance3 = bookInstance(book2, BookAvailability.AVAILABLE, "Mustaches drawn"
        + " on portraits", "1st Edition");
    BookInstance bookInstance4 = bookInstance(book1, BookAvailability.REMOVED, "New",
        "1st Edition");
    BookInstance bookInstance5 = bookInstance(book1, BookAvailability.AVAILABLE, "New, not already borrowed",
        "2nd Modified Edition");

    log.debug("Loaded book instances.");

    User user1 = getNewUser("John Green", "admin", "admin", "4125 7th Ave, New York, NY 10022, "
        + "USA", UserType.EMPLOYEE, BigDecimal.ZERO);
    userService.create(user1, user1.getPasswordHash());

    User user2 = getNewUser("Matt Yellow", "matt", "matt", "Orlando, USA", UserType.CUSTOMER, BigDecimal.TEN);
    userService.create(user2, user2.getPasswordHash());

    User user3 = getNewUser("Simon White", "simon", "simon", "California, USA",
        UserType.CUSTOMER, BigDecimal.ZERO);
    userService.create(user3, user3.getPasswordHash());

    log.debug("Loaded users.");

    Loan loan1 = loan(user1, bookInstance1, new GregorianCalendar(2016, Calendar.JANUARY, 20).getTime(), 30, "normal loan");
    Loan loan2 = loan(user2, bookInstance2, new GregorianCalendar(2016, Calendar.FEBRUARY, 11).getTime(), 30, "normal loan");
    Loan loan3 = loan(user3, bookInstance3, new GregorianCalendar(2016, Calendar.DECEMBER, 15).getTime(), 30, "extended date loan");

  }

  /**
   * Creates book instance according to given parameters.
   *
   * @param book Book.
   * @param bookAvailability Availability of book instance.
   * @param bookState State of book instance.
   * @param bookVersion Version of given book instance
   * @return New book instance object.
   */
  private BookInstance bookInstance(Book book, BookAvailability bookAvailability, String bookState,
                                    String bookVersion) {
    BookInstance bookInstance = new BookInstance();
    bookInstance.setBook(book);
    bookInstance.setBookAvailability(bookAvailability);
    bookInstance.setBookState(bookState);
    bookInstance.setVersion(bookVersion);
    bookInstanceService.addBookInstance(bookInstance);
    return bookInstance;
  }

  /**
   * TODO: Placeholder book() -- modify to your needs
   *
   * @param bookPages Number of pages of a particular book.
   * @param bookName Name of book.
   * @param bookISBN ISBN.
   * @param bookAuthor Author of book.
   * @param bookDescription Description of.
   * @param bookDepartment Department which the book belongs to.
   * @return New book object.
   */
  private Book book(int bookPages, String bookName, String bookISBN, String bookAuthor,
                    String bookDescription, Department bookDepartment) {
    Book book = new Book();
    book.setPages(bookPages);
    book.setName(bookName);
    book.setIsbn(bookISBN);
    book.setAuthor(bookAuthor);
    book.setDescription(bookDescription);
    book.setDepartment(bookDepartment);
    bookService.addBook(book);
    return book;
  }

  /**
   * TODO: Placeholder department() -- modify to your needs
   *
   * @param departmentShortName Short name of department.
   * @param departmentName Name of department.
   * @return New department object.
   */
  private Department department(String departmentShortName, String departmentName) {
    Department department = new Department();
    department.setShortName(departmentShortName);
    department.setName(departmentName);
    departmentService.create(department);
    return department;
  }

  /**
   * Helper method, which creates a new User entity.
   *
   * @param name Name of user.
   * @param login Login of user.
   * @param password User password.
   * @param address Address of user.
   * @param userType Role, which has user in system - CUSTOMER or EMPLOYEE.
   * @param totalFines Total fines of user.
   * @return New user object.
   */
  private User getNewUser(String name, String login, String password, String address,
                             UserType userType, BigDecimal totalFines) {
    User user = new User();
    user.setName(name);
    user.setLogin(login);
    user.setPasswordHash(password);
    user.setAddress(address);
    user.setUserType(userType);
    user.setTotalFines(totalFines);
    return user;
  }

  /**
   * Helper method, which creates a new loan.
   *
   * @param user User, which this loan belongs to.
   * @param bookInstance Which book instance has borrowed the user.
   * @param dateFrom Start dote of loan.
   * @param loanLength How long is the book borrowed.
   * @param loanState State of loan.
   * @return New loan object.
   */
  private Loan loan(User user, BookInstance bookInstance, Date dateFrom, int loanLength, String loanState){
    Loan l = new Loan();
    l.setUser(user);
    l.setBookInstance(bookInstance);
    l.setDateFrom(dateFrom);
    l.setLoanLength(loanLength);
    l.setFine(BigDecimal.ZERO);
    l.setLoanState(loanState);
    loanService.create(l);
    return l;
  }
}
