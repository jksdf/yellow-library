package cz.muni.fi.pa165.yellowlibrary.service.facade;

import org.hibernate.service.spi.ServiceException;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;
import cz.muni.fi.pa165.yellowlibrary.service.BeanMappingService;
import cz.muni.fi.pa165.yellowlibrary.service.BookInstanceService;
import cz.muni.fi.pa165.yellowlibrary.service.configuration.ServiceConfiguration;

/**
 * @author Matej Gallo
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BookInstanceFacadeTest  {

  @InjectMocks
  private BookInstanceService bookInstanceService;

  @Inject
  private BeanMappingService beanMappingService;

  @Inject
  private BookInstanceFacade bookInstanceFacade;

  @BeforeClass
  public void setupMocks() throws ServiceException {
    MockitoAnnotations.initMocks(this);
  }

  private BookInstance bookInstanceOne;

  @BeforeMethod
  public void setupBookInstance() {
    bookInstanceOne = new BookInstance();
    Book book = new Book();
    Department dep = new Department();
    dep.setName("Test Department");
    dep.setShortName("TD");
    book.setIsbn("123-123-123");
    book.setPages(222);
    book.setDescription("The Life of a Tester");
    book.setAuthor("Harry");
    book.setName("Domain");
    book.setDepartment(dep);

    bookInstanceOne.setBookState("NEW");
    bookInstanceOne.setBookAvailability(BookAvailability.AVAILABLE);
    bookInstanceOne.setBook(book);
  }

  @Test
  public void testFindById() {

  }

}
