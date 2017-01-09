package cz.muni.fi.pa165.yellowlibrary.service.facade;

import com.google.common.collect.ImmutableList;

import junit.framework.Assert;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookSearchDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.BookService;
import cz.muni.fi.pa165.yellowlibrary.service.DepartmentService;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.yellowlibrary.service.utils.BookUtils;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author cokinova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookFacadeTest extends AbstractTestNGSpringContextTests {
  @Mock
  private BookService bookService;

  @Mock
  private DepartmentService departmentService;

  @Spy
  @Inject
  private BeanMappingService mappingService;

  @InjectMocks
  private BookFacade bookFacade = new BookFacadeImpl();


  private BookInstance bookInstance1;
  private Department department;
  private Book book1;
  private Book book2;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
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
    when(bookService.getBook(book1.getId())).thenReturn(book1);
    when(bookService.getBook(book2.getId())).thenReturn(book2);
    when(bookService.getAllBooks()).thenReturn(ImmutableList.of(book1, book2));
    when(departmentService.findById(any(Long.class))).thenReturn(department);
    doAnswer(invocation -> {
      Object arg = invocation.getArguments()[0];
      if (arg == null) {
        throw new NullPointerException("Argument cannot be null");
      }
      Book book= (Book) arg;
      if (book.getId() != null) {
        throw new IllegalArgumentException("Book id must be null");
      }
      book.setId(40L);
      return book;
    }).when(bookService).addBook(any(Book.class));

    doAnswer(invocation -> {
      List<BookDTO> list = new ArrayList<>();
      BookDTO bookDTO = mappingService.mapTo(book1, BookDTO.class);
      list.add(bookDTO);
      return list;
    }).when(bookService).searchBooks(any(String.class), any(String.class), any(String.class), any(String.class), any(Set.class));
  }

  @Test
  public void testGetBook(){
    BookDTO foundDTO = bookFacade.getBook(book1.getId());
    verify(bookService).getBook(book1.getId());
    Book book = mappingService.mapTo(foundDTO, Book.class);
    BookUtils.assertDeepEquals(book, book1);
  }

  @Test
  public void testCreateBook(){
    BookCreateDTO bookDTO = mappingService.mapTo(book1, BookCreateDTO.class);
    bookDTO.setId(null);
    bookDTO.setDepartmentId(book1.getDepartment().getId());
    Long b = bookFacade.createBook(bookDTO);
    assertThat(b).isNotNull();
  }

  @Test
  public void testUpdateBook(){
    BookCreateDTO bookDTO = mappingService.mapTo(book1, BookCreateDTO.class);
    bookDTO.setDepartmentId(book1.getDepartment().getId());
    bookFacade.updateBook(bookDTO);
    BookDTO updated = bookFacade.getBook(bookDTO.getId());
    Book updateBook = mappingService.mapTo(updated, Book.class);
    BookUtils.assertDeepEquals(updateBook, book1);
  }

  @Test
  public void testFindBook(){
    BookSearchDTO bookSearchDTO = new BookSearchDTO();
    bookSearchDTO.setName("Spievankovo");

    List<BookDTO> books = bookFacade.findBooks(bookSearchDTO);
    Assert.assertEquals(books.size(),1);
    Book search = mappingService.mapTo(books.get(0), Book.class);
    BookUtils.assertDeepEquals(search, book1);
  }

  @Test
  public void testGetAll() {
    when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));
    Assert.assertEquals(bookFacade.getAll().size(), 2);
    verify(bookService).getAllBooks();
  }
}
