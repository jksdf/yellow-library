package cz.muni.fi.pa165.yellowlibrary.backend;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.backend.dao.BookDao;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;

/**
 * BookDao Test Class
 * @author Matej Gallo
 */

@ContextConfiguration(classes = LibraryApplicationContext.class)
public class BookDaoTest extends AbstractTestNGSpringContextTests {

  @Inject
  private BookDao bookDao;

  private Book bookOne;
  private Book bookTwo;

  @BeforeMethod
  public void prepareBooks() {
    bookOne = new Book();
    bookTwo = new Book();
    
    bookOne.setAuthor("I.B. Bowling");
    bookTwo.setAuthor("Patrick Rothfuss");
    
    bookOne.setIsbn("123-456-789");
    bookTwo.setIsbn("111-222-333");
    
    bookOne.setName("Parry Hotter");
    bookTwo.setName("The Wise Man's Fear");
    
    bookOne.setDescription("Young wizard becomes a normal everyday boy.");
    bookTwo.setDescription("Continuation of young Kvothe's adventures.");

    bookOne.setPages(776);
    bookTwo.setPages(988);
  }

//  Primitive long cannot be null
//
//  @Test(expectedExceptions = NullPointerException.class)
//  public void testRetrieveNullIdBook() {
//    bookDao.getBookFromId(null);
//  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testRemoveNullBook() {
    bookDao.remove(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testCreateNullBook() {
    bookDao.create(null);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testUpdateNullBook() {
    bookDao.update(null);
  }

  @Test
  public void testRetrieveAllBooks() {
    bookDao.create(bookOne);
    bookDao.create(bookTwo);
    Assert.assertEquals(bookDao.getAllBooks().size(), 2);
    Assert.assertTrue(bookDao.getAllBooks().get(0).equals(bookOne));
    Assert.assertTrue(bookDao.getAllBooks().get(1).equals(bookTwo));
  }

  @Test
  public void testRetrieveBookById() {
    bookDao.create(bookOne);
    bookDao.create(bookTwo);

    Assert.assertTrue(bookDao.getBookFromId(bookOne.getId()).equals(bookOne));
    Assert.assertTrue(bookDao.getBookFromId(bookTwo.getId()).equals(bookTwo));
  }

  @Test
  public void testDeleteBook() {
    bookDao.create(bookOne);
    bookDao.create(bookTwo);

    Assert.assertEquals(bookDao.getAllBooks().size(), 2);
    bookDao.remove(bookOne);
    Assert.assertEquals(bookDao.getAllBooks().size(), 1);
    Assert.assertTrue(bookDao.getAllBooks().get(0).equals(bookTwo));
  }

  @Test
  public void testUpdateBook() {
    bookDao.create(bookOne);
    Assert.assertEquals(bookDao.getBookFromId(bookOne.getId()).getName(), "Parry Hotter");
    bookOne = bookDao.getBookFromId(bookOne.getId());
    bookOne.setName("Harry Potter");
    bookDao.update(bookOne);
    Assert.assertEquals(bookDao.getBookFromId(bookOne.getId()).getName(), "Harry Potter");
  }
}
