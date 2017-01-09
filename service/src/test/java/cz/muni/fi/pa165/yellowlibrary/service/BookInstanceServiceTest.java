package cz.muni.fi.pa165.yellowlibrary.service;

import com.google.common.collect.ImmutableList;

import org.hibernate.service.spi.ServiceException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.exceptions.YellowServiceException;
import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookInstanceDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Matej Gallo
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookInstanceServiceTest extends AbstractTestNGSpringContextTests {

  @Mock
  private BookInstanceDao bookInstanceDao;

  @Mock
  private LoanService loanService;

  @Inject
  @InjectMocks
  private BookInstanceService bookInstanceService;

  @BeforeClass
  public void setup() throws ServiceException {
    MockitoAnnotations.initMocks(this);
  }

  private BookInstance bookInstanceOne;
  private Book book;
  private Loan loan;

  @BeforeMethod
  public void prepareBookInstance() {
    bookInstanceOne = new BookInstance();
    book = new Book();
    Department dep = new Department();
    dep.setName("Test Department");
    dep.setShortName("TD");
    book.setIsbn("123-123-123");
    book.setPages(222);
    book.setDescription("The Life of a Tester");
    book.setAuthor("Harry");
    book.setName("Domain");
    book.setDepartment(dep);
    book.setId(12L);

    bookInstanceOne.setBookState("NEW");
    bookInstanceOne.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstanceOne.setBook(book);
    bookInstanceOne.setId(7L);

    loan = new Loan();
    loan.setBookInstance(bookInstanceOne);
  }

  @Test
  public void testAddBookInstance() {
    BookInstance addedBookInstance = bookInstanceService.addBookInstance(bookInstanceOne);
    Assert.assertNotNull(addedBookInstance);
    Assert.assertEquals(addedBookInstance, bookInstanceOne);
    doNothing().when(bookInstanceDao).createBookInstance(any(BookInstance.class));
    verify(bookInstanceDao).createBookInstance(bookInstanceOne);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testAddNullInstance() {
    doThrow(new NullPointerException("BookInstance must be set."))
        .when(bookInstanceDao).createBookInstance(null);

    bookInstanceService.addBookInstance(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testDeleteNullInstance() {
    doThrow(new NullPointerException("BookInstance must be set."))
        .when(bookInstanceDao).deleteBookInstance(null);

    bookInstanceService.deleteBookInstance(null);
  }

  @Test
  public void testDeleteBookInstance() {
    bookInstanceService.deleteBookInstance(bookInstanceOne);
    doNothing().when(bookInstanceDao).deleteBookInstance(any(BookInstance.class));
    verify(bookInstanceDao).deleteBookInstance(bookInstanceOne);
  }

  @Test(expectedExceptions = YellowServiceException.class)
  public void testDeleteBookInstanceBorrowed() {
    when(loanService.currentLoanOfBookInstance(bookInstanceOne)).thenReturn(loan);
    bookInstanceService.deleteBookInstance(bookInstanceOne);
  }

  @Test
  public void testSetBook() {
    Department dep = new Department();
    dep.setName("NEW DEPARTMENT");
    dep.setShortName("ND");

    Book newBook = new Book();
    newBook.setName("This Book");
    newBook.setPages(123);
    newBook.setDepartment(dep);

    BookInstance newBookInstance = new BookInstance();
    newBookInstance.setBook(newBook);
    newBookInstance.setBookAvailability(bookInstanceOne.getBookAvailability());
    newBookInstance.setId(bookInstanceOne.getId());
    newBookInstance.setBookState(bookInstanceOne.getBookState());
    newBookInstance.setVersion(bookInstanceOne.getVersion());

    doNothing().when(bookInstanceDao).updateBookInstance(any(BookInstance.class));

    bookInstanceService.setBook(bookInstanceOne, newBook);

    verify(bookInstanceDao).updateBookInstance(newBookInstance);
    Assert.assertEquals(bookInstanceOne.getBook(), newBook);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testSetNullBook() {
    ArgumentCaptor<BookInstance> arg = ArgumentCaptor.forClass(BookInstance.class);

    doThrow(new NullPointerException("BookInstance cannot be null."))
        .when(bookInstanceDao).updateBookInstance(any(BookInstance.class));

    bookInstanceService.setBook(bookInstanceOne, null);
    verify(bookInstanceDao).updateBookInstance(arg.capture());
    Assert.assertNull(arg.getValue().getBook());
  }

  @Test
  public void testChangeState() {
    String newState = "Page 34 torn out";

    BookInstance newBookInstance = new BookInstance();
    newBookInstance.setBookAvailability(bookInstanceOne.getBookAvailability());
    newBookInstance.setBook(bookInstanceOne.getBook());
    newBookInstance.setVersion(bookInstanceOne.getVersion());
    newBookInstance.setBookState(newState);
    newBookInstance.setId(bookInstanceOne.getId());

    doNothing().when(bookInstanceDao).updateBookInstance(any(BookInstance.class));

    bookInstanceService.changeState(bookInstanceOne, newState);
    verify(bookInstanceDao).updateBookInstance(newBookInstance);
    Assert.assertEquals(bookInstanceOne.getBookState(), newState);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testSetNullState() {
    ArgumentCaptor<BookInstance> arg = ArgumentCaptor.forClass(BookInstance.class);
    doThrow(new NullPointerException("BookInstance cannot be null."))
        .when(bookInstanceDao).updateBookInstance(any(BookInstance.class));

    bookInstanceService.changeState(bookInstanceOne, null);
    verify(bookInstanceDao).updateBookInstance(arg.capture());
    Assert.assertNull(arg.getValue().getBookState());
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testChangeNullAvailability() {
    ArgumentCaptor<BookInstance> arg = ArgumentCaptor.forClass(BookInstance.class);
    doThrow(new NullPointerException("BookInstance cannot be null."))
        .when(bookInstanceDao).updateBookInstance(any(BookInstance.class));

    bookInstanceService.changeAvailability(bookInstanceOne, null);
    verify(bookInstanceDao).updateBookInstance(arg.capture());
    Assert.assertNull(arg.getValue().getBookAvailability());
  }

  @Test
  public void testChangeAvailability() {
    BookInstance newBookInstance = new BookInstance();
    newBookInstance.setBookAvailability(BookAvailability.REMOVED);
    newBookInstance.setBook(bookInstanceOne.getBook());
    newBookInstance.setId(bookInstanceOne.getId());
    newBookInstance.setVersion(bookInstanceOne.getVersion());
    newBookInstance.setBookState(bookInstanceOne.getBookState());

    doNothing().when(bookInstanceDao).updateBookInstance(any(BookInstance.class));

    bookInstanceService.changeAvailability(bookInstanceOne, BookAvailability.REMOVED);
    verify(bookInstanceDao).updateBookInstance(newBookInstance);
    Assert.assertEquals(bookInstanceOne.getBookAvailability(), BookAvailability.REMOVED);
  }

  @Test
  public void testGetBookInstanceById() {
    when(bookInstanceDao.findById(bookInstanceOne.getId())).thenReturn(bookInstanceOne);

    BookInstance foundBookInstance = bookInstanceService.getBookInstanceById(bookInstanceOne.getId());
    verify(bookInstanceDao).findById(bookInstanceOne.getId());
    Assert.assertEquals(foundBookInstance, bookInstanceOne);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testGetBookInstanceByNullId() {
    when(bookInstanceDao.findById(null))
        .thenThrow(new NullPointerException("ID must be specified."));

    bookInstanceService.getBookInstanceById(null);
    verify(bookInstanceDao).findById(null);
  }

  @Test
  public void testGetAllBookInstances() {
    BookInstance bookInstanceTwo = new BookInstance();
    bookInstanceTwo.setBookAvailability(BookAvailability.BORROWED);
    bookInstanceTwo.setBook(book);
    bookInstanceTwo.setBookState("Coffee spilt");
    bookInstanceTwo.setVersion("2nd Edition");
    bookInstanceTwo.setId(111L);

    when(bookInstanceDao.findAll()).thenReturn(ImmutableList.of(bookInstanceOne, bookInstanceTwo));

    List<BookInstance> result = bookInstanceService.getAllBookInstances();
    Assert.assertTrue(result.size() == 2);
    Assert.assertTrue(result.containsAll(ImmutableList.of(bookInstanceOne, bookInstanceTwo)));
  }

}