package cz.muni.fi.pa165.yellowlibrary.sampledata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
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

  final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

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

    Book book1 = book(100, "Technological Advacements on Farms", "577571257", "Joe Backhand",
        "Short description of robotic advacements on local farms.", dep1);
    Book book2 = book(20, "How to play tennis with wheat straws", "2992901818", "Joe Backhand",
        "Brief overview on how to make tennic racket using only wheat straws.", dep1);
    Book book3 = book(123, "Safecorp Industries Obituaries", "99000900", "Marcus Coffin",
        "Safecorp Industries Obituaries 1966", dep2);

    log.debug("Loaded books.");

    BookInstance bookInstance1 = bookInstance(book1, BookAvailability.AVAILABLE, "Page 4 torn out",
        "2nd Edition");
    BookInstance bookInstance2 = bookInstance(book1, BookAvailability.AVAILABLE, "Cover damaged",
        "2nd Edition");
    BookInstance bookInstance3 = bookInstance(book2, BookAvailability.BORROWED, "Mustaches drawn"
        + " on portraits", "1st Edition");

    log.debug("Loaded book instances.");
  }

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

  /*
   * TODO: Placeholder book() -- modify to your needs
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

  /*
   * TODO: Placeholder department() -- modify to your needs
   */
  private Department department(String departmentShortName, String departmentName) {
    Department department = new Department();
    department.setShortName(departmentShortName);
    department.setName(departmentName);
    departmentService.create(department);
    return department;
  }
}
