package cz.muni.fi.pa165.yellowlibrary.service.facade;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.BookInstanceService;
import cz.muni.fi.pa165.yellowlibrary.service.BookService;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matej Gallo
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookInstanceFacadeTest extends AbstractTestNGSpringContextTests {

  @Mock
  private BookInstanceService bookInstanceService;

  @Mock
  private BookService bookService;

  @Spy
  @Inject
  private BeanMappingService beanMappingService;

  @InjectMocks
  private BookInstanceFacade bookInstanceFacade = new BookInstanceFacadeImpl();

  private BookInstance bookInstanceOne;
  private BookInstance newBookInstance;
  private Book book;
  private Department dep;

  @BeforeMethod
  public void setupBookInstance() {
    MockitoAnnotations.initMocks(this);
    bookInstanceOne = new BookInstance();
    book = new Book();
    dep = new Department();
    dep.setName("Test Department");
    dep.setShortName("TD");
    book.setIsbn("123-123-123");
    book.setPages(222);
    book.setDescription("The Life of a Tester");
    book.setAuthor("Harry");
    book.setName("Domain");
    book.setDepartment(dep);
    book.setId(13L);

    bookInstanceOne.setBookState("NEW");
    bookInstanceOne.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstanceOne.setBook(book);
    bookInstanceOne.setId(42L);

    newBookInstance = new BookInstance();
    newBookInstance.setBookAvailability(bookInstanceOne.getBookAvailability());
    newBookInstance.setBook(bookInstanceOne.getBook());
    newBookInstance.setId(bookInstanceOne.getId());
    newBookInstance.setVersion(bookInstanceOne.getVersion());
    newBookInstance.setBookState(bookInstanceOne.getBookState());
  }

  @Test
  public void testFindById() {
    when(bookInstanceService.getBookInstanceById(bookInstanceOne.getId()))
        .thenReturn(bookInstanceOne);
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(bookInstanceOne.getId());
    verify(bookInstanceService).getBookInstanceById(bookInstanceOne.getId());
    BookInstance bookInstance = beanMappingService.mapTo(bookInstanceDTO, BookInstance.class);
    Assert.assertEquals(bookInstance, bookInstanceOne);
  }

  /*@Test
  public void testAddBookInstance() {
    BookInstanceDTO bookInstanceDTO = beanMappingService.mapTo(bookInstanceOne, BookInstanceDTO);
    bookInstanceFacade.createBookInstance(bookInstanceDTO);
    verify(bookInstanceService).addBookInstance(any(BookInstance.class));
  }*/

  @Test
  public void testCreateBookInstance() {
    BookInstanceCreateDTO bookInstanceDTO = new BookInstanceCreateDTO();
    bookInstanceDTO.setBookAvailability(BookInstanceAvailability.AVAILABLE);
    bookInstanceDTO.setBookId(13L);
    bookInstanceDTO.setBookState("NEW");

    when(bookService.getBook(13L)).thenReturn(book);
    when(bookInstanceService.addBookInstance(any(BookInstance.class))).thenReturn(bookInstanceOne);

    Long id = bookInstanceFacade.createBookInstance(bookInstanceDTO);
    Assert.assertEquals(id, bookInstanceOne.getId());
  }

  @Test
  public void testChangeBookState() {
    when(bookInstanceService.getBookInstanceById(bookInstanceOne.getId()))
        .thenReturn(bookInstanceOne);
    BookInstanceNewStateDTO newStateDTO = new BookInstanceNewStateDTO();
    String newState = "THIS IS NEW STATE";
    newStateDTO.setId(bookInstanceOne.getId());
    newStateDTO.setBookState(newState);
    bookInstanceFacade.changeBookState(newStateDTO);
    verify(bookInstanceService).changeState(bookInstanceOne, newState);
  }

  @Test
  public void testChangeBookAvailability() {
    when(bookInstanceService.getBookInstanceById(bookInstanceOne.getId()))
        .thenReturn(bookInstanceOne);
    bookInstanceFacade.changeBookAvailability(bookInstanceOne.getId(), BookInstanceAvailability.REMOVED);

    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(bookInstanceOne.getId());
    BookInstance bookInstance = beanMappingService.mapTo(bookInstanceDTO, BookInstance.class);

    verify(bookInstanceService).changeAvailability(bookInstance, BookAvailability.REMOVED);
  }

  @Test
  public void testSetBook() {
    when(bookInstanceService.getBookInstanceById(bookInstanceOne.getId()))
        .thenReturn(bookInstanceOne);
    Book newBook = new Book();
    newBook.setDepartment(dep);
    newBook.setName("NEW BOOK");
    newBook.setPages(123);
    newBook.setId(15L);
    when(bookService.getBook(15L)).thenReturn(newBook);
    bookInstanceFacade.setBook(bookInstanceOne.getId(), 15L);
    verify(bookInstanceService).setBook(bookInstanceOne, newBook);
  }

  @Test
  public void testGetAllBookInstances() {
    newBookInstance.setId(55L);
    newBookInstance.setBookState("THIS IS SECOND BOOK");
    when(bookInstanceService.getAllBookInstances()).thenReturn(
        ImmutableList.of(bookInstanceOne, newBookInstance));

    List<BookInstance> bookInstanceList = beanMappingService.mapTo(bookInstanceFacade.getAllBookInstances(),
        BookInstance.class);

    Assert.assertEquals(bookInstanceList.size(), 2);
    Assert.assertTrue(bookInstanceList.containsAll(ImmutableList.of(bookInstanceOne, newBookInstance)));
  }

  @Test
  public void testGetAllBorrowedInstances() {
    newBookInstance.setBookAvailability(BookAvailability.BORROWED);
    when(bookInstanceService.getAllBookInstances()).thenReturn(
        ImmutableList.of(bookInstanceOne, newBookInstance));
    List<BookInstance> bookInstanceList = beanMappingService.mapTo(bookInstanceFacade.getAllBorrowedBookInstances(),
        BookInstance.class);

    Assert.assertEquals(bookInstanceList.size(), 1);
    Assert.assertTrue(bookInstanceList.contains(newBookInstance));
  }

  @Test
  public void testGetAllCopies() {
    newBookInstance.setBookAvailability(BookAvailability.BORROWED);
    newBookInstance.setBookState("Slightly Torn");
    book.setBookInstances(ImmutableSet.of(bookInstanceOne, newBookInstance));

    when(bookService.getBook(book.getId())).thenReturn(book);
    List<BookInstance> bookInstanceList = beanMappingService.mapTo(bookInstanceFacade.getAllCopies(book.getId()), BookInstance.class);
    Assert.assertEquals(bookInstanceList.size(), 2);
    Assert.assertTrue(bookInstanceList.containsAll(ImmutableList.of(bookInstanceOne, newBookInstance)));
  }

}
