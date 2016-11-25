package cz.muni.fi.pa165.yellowlibrary.service.facade;

import com.google.common.collect.ImmutableList;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookSearchDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UpdateBookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.BookService;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.yellowlibrary.service.utils.BookUtils;

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
    DepartmentDTO departmentDTO = mappingService.mapTo(department, DepartmentDTO.class);
    UpdateBookDTO bookDTO = mappingService.mapTo(book1, UpdateBookDTO.class);
    bookDTO.setDepartmentId(departmentDTO);
    long createdId = bookFacade.createBook(bookDTO);
    verify(bookService).getBook(book1.getId());
    Book book = mappingService.mapTo(bookDTO, Book.class);
    BookUtils.assertDeepEquals(book, book1);
  }
}