package cz.muni.fi.pa165.yellowlibrary.backend;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Null;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookInstanceDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;

import static org.testng.Assert.*;

/**
 * Class for testing BookInstance
 * @author Jozef Zivcic
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class BookInstanceDaoTest extends AbstractTestNGSpringContextTests {

  @Inject
  private BookInstanceDao bookInstanceDao;

  @PersistenceContext
  private EntityManager em;

  private BookInstance firstBookInstance;
  private BookInstance secondBookInstance;

  @BeforeMethod
  public void setUp() {
    Book b1 = new Book();
    Book b2 = new Book();
    firstBookInstance = new BookInstance();
    secondBookInstance = new BookInstance();


    Department d1 = createDepartment("Department 1", "D1", b1);
    b1 = setBook(firstBookInstance, b1, "Joshua Bloch", "Effective Java brings together"
        + " seventy-eight indispensable programmer’s rules of thumb", "Effective Java", 346,
        "978-0321356680", d1);
    em.persist(d1);
    em.persist(b1);

    firstBookInstance.setBookAvailability(BookAvailability.AVAILABLE);
    firstBookInstance.setBookState("new");
    firstBookInstance.setVersion("second");
    firstBookInstance.setBook(b1);


    Department d2 = createDepartment("Department 2", "D2", b2);
    b2 = setBook(secondBookInstance, b2, "Erich Gamma et al.", "What patterns are and how they can "
        + "help you design object-oriented software.", "Design Patterns: Elements of Reusable "
        + "Object-Oriented Software", 395, "978-0201633610", d2);
    em.persist(d2);
    em.persist(b2);

    secondBookInstance.setBookAvailability(BookAvailability.BORROWED);
    secondBookInstance.setBookState("new");
    secondBookInstance.setBook(b2);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createNullBookInstanceTest() {
    bookInstanceDao.createBookInstance(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void deleteNullBookInstanceTest() {
    bookInstanceDao.deleteBookInstance(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void updateNullBookInstanceTest() {
    bookInstanceDao.updateBookInstance(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void findByNullIdBookInstanceTest() {
    bookInstanceDao.findById(null);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createBookInstanceWithNullBookStateTest() {
    BookInstance bookInstance = getBookInstanceWithNullState();
    bookInstanceDao.createBookInstance(bookInstance);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void createBookInstanceWithNullBookAvailabilityTest() {
    BookInstance bookInstance = getBookInstanceWithNullAvailability();
    bookInstanceDao.createBookInstance(bookInstance);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void updateBookInstanceWithNullBookStateTest() {
    BookInstance bookInstance = getBookInstanceWithNullState();
    bookInstanceDao.updateBookInstance(bookInstance);
  }

  @Test(expectedExceptions = ConstraintViolationException.class)
  public void updateBookInstanceWithNullBookAvailabilityTest() {
    BookInstance bookInstance = getBookInstanceWithNullAvailability();
    bookInstanceDao.updateBookInstance(bookInstance);
  }

  @Test
  public void createAndGetBookInstanceTest() {
    bookInstanceDao.createBookInstance(firstBookInstance);
    BookInstance ret = bookInstanceDao.findById(firstBookInstance.getId());
    assertDeepEquals(firstBookInstance,ret);
  }

  @Test
  public void createAndGetTwoBookInstancesTest() {
    bookInstanceDao.createBookInstance(firstBookInstance);
    bookInstanceDao.createBookInstance(secondBookInstance);
    BookInstance ret = bookInstanceDao.findById(firstBookInstance.getId());
    assertDeepEquals(firstBookInstance, ret);
    ret = bookInstanceDao.findById(secondBookInstance.getId());
    assertDeepEquals(secondBookInstance, ret);
  }

  @Test
  public void createAndDeleteBookTest() {
    bookInstanceDao.createBookInstance(firstBookInstance);
    BookInstance ret = bookInstanceDao.findById(firstBookInstance.getId());
    assertDeepEquals(firstBookInstance, ret);
    bookInstanceDao.deleteBookInstance(firstBookInstance);
    ret = bookInstanceDao.findById(firstBookInstance.getId());
    assertNull(ret);
  }

  @Test
  public void createAndDeleteTwoBooksTest() {
    bookInstanceDao.createBookInstance(firstBookInstance);
    bookInstanceDao.createBookInstance(secondBookInstance);
    bookInstanceDao.deleteBookInstance(firstBookInstance);
    BookInstance ret = bookInstanceDao.findById(firstBookInstance.getId());
    assertNull(ret);
    ret = bookInstanceDao.findById(secondBookInstance.getId());
    assertDeepEquals(secondBookInstance, ret);
    bookInstanceDao.deleteBookInstance(secondBookInstance);
    ret = bookInstanceDao.findById(secondBookInstance.getId());
    assertNull(ret);
  }

  @Test
  public void findUnexistingBookInstanceTest() {
    BookInstance ret = bookInstanceDao.findById(123456789L);
    assertNull(ret);
  }

  @Test
  public void updateBookInstanceTest() {
    String bookState = "new testing state";
    String bookVersion = "new testing state";
    bookInstanceDao.createBookInstance(firstBookInstance);
    BookInstance updated = new BookInstance();
    updated.setBook(firstBookInstance.getBook());
    updated.setId(firstBookInstance.getId());
    updated.setBookAvailability(BookAvailability.BORROWED);
    updated.setBookState(bookState);
    updated.setVersion(bookVersion);
    bookInstanceDao.updateBookInstance(updated);
    BookInstance ret = bookInstanceDao.findById(updated.getId());
    assertDeepEquals(updated, ret);
  }

  /**
   * This method updates @param book with attributes specified as parameters.
   * @param bookInstance BookInstance entity specified for updated book.
   * @param book Book to be updated.
   * @param author Author of updated book.
   * @param description Description of updated book.
   * @param name Name of updated book.
   * @param pages Number of pages of updated book.
   * @param isbn ISBN for updated book.
   * @param department Department of updated book.
   * @return Updated book.
   */
  private Book setBook(BookInstance bookInstance, Book book, String author, String description,
                                                  String name, int pages, String isbn,
                                                  Department department) {
    book.setAuthor(author);
    book.setDescription(description);
    book.setIsbn(isbn);
    book.setName(name);
    book.setPages(pages);
    HashSet<BookInstance> instances = new HashSet<>();
    instances.add(bookInstance);
    book.setBookInstances(instances);
    book.setDepartment(department);
    return book;
  }

  /**
   * Creates new Department entity according to attributes specified as parameters.
   * @param deptName Name of department.
   * @param dCode Department code.
   * @param book One book that belongs to department.
   * @return New Department entity.
   */
  private Department createDepartment(String deptName, String dCode, Book book) {
    Department d = new Department();
    d.setName(deptName);
    d.setShortName(dCode);
    List<Book> books = new ArrayList<>();
    books.add(book);
    d.setBooks(books);
    return d;
  }

  /**
   * Creates sample BookInstance entity, that is properly created except state attribute, which is
   * null.
   * @return Sample BookInstance entity.
   */
  private BookInstance getBookInstanceWithNullState() {
    BookInstance bookInstance = new BookInstance();
    Book book = new Book();
    Department department = createDepartment("Department", "D", book);
    book = setBook(bookInstance, book, "author", "desc", "name", 100, "ISBN", department);
    bookInstance.setBookState(null);
    bookInstance.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstance.setBook(book);
    em.persist(department);
    em.persist(book);
    return bookInstance;
  }

  /**
   * Creates sample BookInstance entity, that is properly created except availability attribute,
   * which is null.
   * @return Sample BookInstance entity.
   */
  private BookInstance getBookInstanceWithNullAvailability() {
    BookInstance bookInstance = new BookInstance();
    Book book = new Book();
    Department department = createDepartment("Department", "D", book);
    book = setBook(bookInstance, book, "author", "desc", "name", 100, "ISBN", department);
    bookInstance.setBookState("new");
    bookInstance.setBookAvailability(null);
    bookInstance.setBook(book);
    em.persist(department);
    em.persist(book);
    return bookInstance;
  }

  /**
   * Controls if all attributes of BookInstance are as expected and tests if two BookInstances are
   * the same using .equals method.
   * @param expected Expected BookInstance entity.
   * @param actual Given BookInstance entity.
   */
  private void assertDeepEquals(BookInstance expected, BookInstance actual) {
    assertEquals(actual.getId(), expected.getId());
    assertEquals(actual.getVersion(), expected.getVersion());
    assertEquals(actual.getBookAvailability(), expected.getBookAvailability());
    assertEquals(actual.getBook(), expected.getBook());
    assertEquals(actual.getBookState(), expected.getBookState());
    assertEquals(expected, actual);
  }
}