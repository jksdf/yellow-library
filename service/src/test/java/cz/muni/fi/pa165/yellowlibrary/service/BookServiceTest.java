package cz.muni.fi.pa165.yellowlibrary.service;

import com.google.common.collect.ImmutableList;

import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 *  @author cokinova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookServiceTest extends AbstractTestNGSpringContextTests {
  @Mock
  private BookDao bookDao;

  @Inject
  @InjectMocks
  private BookService bookService;

  private BookInstance bookInstance1;
  private Department department;
  private Book book1;
  private Book book2;

  @BeforeClass
  public void setup() throws ServiceException {
    MockitoAnnotations.initMocks(this);
  }

  @BeforeMethod
  public void setUp() {
    department = new Department();
    department.setId(0L);
    department.setName("Department");
    department.setShortName("DEP");

    book1 = new Book();
    book1.setId(40L);
    book1.setName("Spievankovo");
    book1.setDepartment(department);
    book1.setAuthor("Autor");
    book1.setDescription("For kids");
    book1.setIsbn("1234567");
    book1.setPages(34);

    book2 = new Book();
    book2.setId(41L);
    book2.setName("Narnia");
    book2.setDepartment(department);
    book2.setAuthor("Autor2");
    book2.setDescription("First Part");
    book2.setIsbn("1112223");
    book2.setPages(135);

    department.setBooks(ImmutableList.of(book1, book2));

    bookInstance1 = new BookInstance();
    bookInstance1.setId(111L);
    bookInstance1.setBook(book1);
    bookInstance1.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstance1.setBookState("Fine");
    bookInstance1.setVersion("2. revision");

    MockitoAnnotations.initMocks(this);
    when(bookDao.getBookFromId(book1.getId())).thenReturn(book1);
    when(bookDao.getBookFromId(book2.getId())).thenReturn(book2);
    when(bookDao.getAllBooks()).thenReturn(ImmutableList.of(book1, book2));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null)
        throw new NullPointerException("Argument cannot be null");
      Book user = (Book) arg;
      if (user.getId() != null)
        throw new NullPointerException("User id must be null");
      user.setId(book1.getId());
      return null;
    }).when(bookDao).create(any(Book.class));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null)
        throw new NullPointerException("Argument cannot be null");
      Book user = (Book) arg;
      if (user.getId() == null)
        throw new NullPointerException("User id must be filled");
      return null;
    }).when(bookDao).update(any(Book.class));

    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null)
        throw new NullPointerException("Argument cannot be null");
      Book user = (Book) arg;
      if (user.getId() == null)
        throw new NullPointerException("User id has to be filled");
      bookDao.getBookFromId(user.getId());
      return null;
    }).when(bookDao).remove(any(Book.class));
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testAddBookNull(){
    bookService.addBook(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testAddBookSetId(){
    Book book = new Book();
    book.setId(42L);
    book.setName("Harry Potter");
    book.setPages(500);
    HashSet<BookInstance> bookInstances = new HashSet<>();
    bookInstances.add(bookInstance1);
    book.setBookInstances(bookInstances);
    book.setDepartment(department);
    bookService.addBook(book);
  }

  @Test
  public void testAddBookOk(){
    Book book = new Book();
    book.setName("Harry Potter");
    book.setPages(500);
    HashSet<BookInstance> bookInstances = new HashSet<>();
    bookInstances.add(bookInstance1);
    book.setBookInstances(bookInstances);
    book.setDepartment(department);
    bookService.addBook(book);
    assertThat(book.getId()).isNotNull();
    verify(bookDao).create(book);
    verifyNoMoreInteractions(bookDao);
  }

  @Test
  public void testGetBook(){
    Book book = bookService.getBook(book1.getId());
    verify(bookDao).getBookFromId(book1.getId());
    verifyNoMoreInteractions(bookDao);
    assertDeepEquals(book1, book);
  }

  @Test
  public void testGetBookNonExisting(){
    Book book = bookService.getBook(10000);
    assertThat(book).isNull();
  }

  @Test
  public void testGetAllBooks(){
    List<Book> books = bookService.getAllBooks();
    assertEquals(books.size(), 2);
  }

  @Test
  public void testUpdateBook(){
    Book book = bookService.getBook(book1.getId());
    book1.setName("New Name");
    bookService.editBook(book1);
    assertDeepEquals(book, book1);
  }

  @Test
  public void testbookSearchAuthor() {
    Set<Long> departments = new HashSet<>();
    List<Book> books = bookService.searchBooks(book2.getAuthor(), null, null, null, null);
    assertThat(books).hasSize(1);
    assertDeepEquals(books.get(0), book2);
  }

  private void assertDeepEquals(Book book1, Book book2) {
    assertThat(book1.getId()).isEqualTo(book2.getId());
    assertThat(book1.getIsbn()).isEqualTo(book2.getIsbn());
    assertThat(book1.getAuthor()).isEqualTo(book2.getAuthor());
    assertThat(book1.getDepartment()).isEqualTo(book2.getDepartment());
    assertThat(book1.getName()).isEqualTo(book2.getName());
    assertThat(book1.getPages()).isEqualTo(book2.getPages());
    assertThat(book1.getDescription()).isEqualTo(book2.getDescription());
    assertThat(book1.getBookInstances()).isEqualTo(book2.getBookInstances());
  }
}
