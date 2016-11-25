package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.hibernate.service.spi.ServiceException;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.BookInstanceService;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Matej Gallo
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookInstanceFacadeTest extends AbstractTestNGSpringContextTests {

  @Mock
  private BookInstanceService bookInstanceService;

  @Spy
  @Inject
  private BeanMappingService beanMappingService;

  @InjectMocks
  private BookInstanceFacade bookInstanceFacade = new BookInstanceFacadeImpl();

  @BeforeClass
  public void setupMocks() throws ServiceException {
    MockitoAnnotations.initMocks(this);
  }

  private BookInstance bookInstanceOne;
  private Book book;

  @BeforeMethod
  public void setupBookInstance() {
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
    book.setId(13L);

    bookInstanceOne.setBookState("NEW");
    bookInstanceOne.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstanceOne.setBook(book);
    bookInstanceOne.setId(42L);
  }

  @Test
  public void testFindById() {
    when(bookInstanceService.getBookInstanceById(any(Long.class)))
        .thenReturn(bookInstanceOne);

    BookInstanceDTO bookInstance = beanMappingService.mapTo(bookInstanceOne, BookInstanceDTO.class);

  }

  /*@Test
  public void testCreateBookInstance() {
    BookInstanceCreateDTO bookInstanceDTO = new BookInstanceCreateDTO();
    bookInstanceDTO.setBookInstanceAvailability(BookInstanceAvailability.AVAILABLE);
    bookInstanceDTO.setBookId(13L);
    bookInstanceDTO.setBookState("NEW");

    when(bookInstanceService.addBookInstance(any(BookInstance.class))).thenReturn(bookInstanceOne);

    Long id = bookInstanceFacade.createBookInstance(bookInstanceDTO);
    Assert.assertEquals(id, bookInstanceOne.getId());
  }*/

}
