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

import java.util.HashSet;

import javax.inject.Inject;
import javax.validation.constraints.Null;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookInstanceDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
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

  //TODO: Uncomment methods that use Dao.update method

  @Inject
  private BookInstanceDao bookInstanceDao;

  private BookInstance firstBookInstance;
  private BookInstance secondBookInstance;

  @BeforeMethod
  public void setUp() {
    Book b1 = new Book();
    Book b2 = new Book();
    firstBookInstance = new BookInstance();
    secondBookInstance = new BookInstance();

    firstBookInstance.setBookAvailability(BookAvailability.AVAILABLE);
    firstBookInstance.setBookState("new");
    firstBookInstance.setVersion("second");
    b1.setAuthor("Joshua Bloch");
    b1.setDescription("Effective Java brings together seventy-eight indispensable programmer’s"
        + " rules of thumb");
    b1.setIsbn("978-0321356680");
    b1.setName("Effective Java");
    b1.setPages(346);
    HashSet<BookInstance> instances1 = new HashSet<>();
    instances1.add(firstBookInstance);
    b1.setBookInstances(instances1);
    firstBookInstance.setBook(b1);

    secondBookInstance.setBookAvailability(BookAvailability.BORROWED);
    secondBookInstance.setBookState("new");
    b2.setAuthor("Erich Gamma et al.");
    b2.setDescription("What patterns are and how they can help you design"
        + " object-oriented software.");
    b2.setIsbn("978-0201633610");
    b2.setName("Design Patterns: Elements of Reusable Object-Oriented Software");
    b2.setPages(395);
    HashSet<BookInstance> instances2 = new HashSet<>();
    instances2.add(secondBookInstance);
    b2.setBookInstances(instances2);
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

//  @Test(expectedExceptions = NullPointerException.class)
//  public void updateNullBookInstanceTest() {
//    bookInstanceDao.updateBookInstance(null);
//  }

  @Test(expectedExceptions = NullPointerException.class)
  public void findByNullIdBookInstanceTest() {
    bookInstanceDao.findById(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createBookInstanceWithNullBookStateTest() {
    BookInstance bookInstance = getBookInstanceWithNullState();
    bookInstanceDao.createBookInstance(bookInstance);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void createBookInstanceWithNullBookAvailabilityTest() {
    BookInstance bookInstance = getBookInstanceWithNullAvailability();
    bookInstanceDao.createBookInstance(bookInstance);
  }

//  @Test(expectedExceptions = NullPointerException.class)
//  public void updateBookInstanceWithNullBookStateTest() {
//    BookInstance bookInstance = getBookInstanceWithNullState();
//    bookInstanceDao.updateBookInstance(bookInstance);
//  }
//
//  @Test(expectedExceptions = NullPointerException.class)
//  public void updateBookInstanceWithNullBookAvailabilityTest() {
//    BookInstance bookInstance = getBookInstanceWithNullAvailability();
//    bookInstanceDao.updateBookInstance(bookInstance);
//  }

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
//    bookInstanceDao.updateBookInstance(updated);
    BookInstance ret = bookInstanceDao.findById(updated.getId());
    assertDeepEquals(updated, ret);
  }

  private BookInstance getBookInstanceWithNullState() {
    Book book = new Book();
    BookInstance bookInstance = new BookInstance();
    bookInstance.setBookState(null);
    bookInstance.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstance.setBook(book);
    return bookInstance;
  }

  private BookInstance getBookInstanceWithNullAvailability() {
    Book book = new Book();
    BookInstance bookInstance = new BookInstance();
    bookInstance.setBookState("Borrowed");
    bookInstance.setBookAvailability(null);
    bookInstance.setBook(book);
    return bookInstance;
  }

  private void assertDeepEquals(BookInstance expected, BookInstance actual) {
    assertEquals(actual.getId(), expected.getId());
    assertEquals(actual.getVersion(), expected.getVersion());
    assertEquals(actual.getBookAvailability(), expected.getBookAvailability());
    assertEquals(actual.getBook(), expected.getBook());
    assertEquals(actual.getBookState(), expected.getBookState());
    assertEquals(expected, actual);
  }
}