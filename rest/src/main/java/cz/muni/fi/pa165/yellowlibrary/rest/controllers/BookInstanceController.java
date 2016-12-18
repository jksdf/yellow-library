package cz.muni.fi.pa165.yellowlibrary.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.BookInstanceAvailability;
import cz.muni.fi.pa165.yellowlibrary.api.exceptions.YellowServiceException;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.rest.ApiUris;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.ResourceAlreadyExists;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.InvalidParameterException;
/**
 * For testing methods on this controller see file /rest/readme.txt
 *
 * Created by Matej Gallo
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_BOOKINSTANCES)
public class BookInstanceController {

  final static Logger log = LoggerFactory.getLogger(BookInstanceController.class);

  @Inject
  private BookInstanceFacade bookInstanceFacade;


  /**
   * Returns all book instances
   * curl -i -X GET http://localhost:8080/pa165/rest/bookinstance
   *
   * @return List<BookInstanceDTO>
   */
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final List<BookInstanceDTO> getBookInstances(@RequestParam(required = false) Long bid,
                                                      @RequestParam(required = false, defaultValue = "all") String filter) {
    log.debug("REST getBookInstances()", bid, filter);
    List<BookInstanceDTO> result;

    switch(filter) {
      case "borrowed":
        if (bid == null) {
          result = bookInstanceFacade.getAllBookInstancesByAvailability(BookInstanceAvailability.BORROWED);
        }
        else {
          result = bookInstanceFacade.getAllCopiesByAvailability(bid, BookInstanceAvailability.BORROWED);
        }
        break;
      case "removed":
        if (bid == null) {
          result = bookInstanceFacade.getAllBookInstancesByAvailability(BookInstanceAvailability.REMOVED);
        }
        else {
          result = bookInstanceFacade.getAllCopiesByAvailability(bid,BookInstanceAvailability.REMOVED);
        }
        break;
      case "available":
        if (bid == null) {
          result = bookInstanceFacade.getAllBookInstancesByAvailability(BookInstanceAvailability.AVAILABLE);
        } else {
          result = bookInstanceFacade.getAllCopiesByAvailability(bid, BookInstanceAvailability.AVAILABLE);
        }
        break;
      case "all":
      default:
        if (bid == null) {
          result = bookInstanceFacade.getAllBookInstances();
        }
        else {
          result = bookInstanceFacade.getAllCopies(bid);
        }
        break;
    }
    result.sort(Comparator.comparing(BookInstanceDTO::getId));
    return result;
  }

  /**
   * Returns a book instance with particular ID
   * curl -i -X GET http://localhost:8080/pa165/rest/bookinstance/1
   *
   * @param id id of a book instance
   * @throws ResourceNotFoundException
   * @return BookInstanceDTO
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final BookInstanceDTO getBookInstance(@PathVariable("id") Long id) throws Exception {
    log.debug("REST getBookInstance({})", id);
    BookInstanceDTO bookInstanceDTO = bookInstanceFacade.findById(id);
    if(bookInstanceDTO != null) {
      return bookInstanceDTO;
    } else {
      throw new ResourceNotFoundException();
    }
  }

  /**
   * Deletes a book instance with a particular id
   * curl -i -X DELETE http://localhost:8080/pa165/rest/bookinstance/1
   *
   * @param id id of a book instance
   * @throws ResourceNotFoundException
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public final void deleteBookInstance(@PathVariable("id") Long id) throws Exception {
    log.debug("REST deleteBookInstance({})", id);
    try {
      bookInstanceFacade.deleteBookInstance(id);
    } catch(YellowServiceException yes) {
      throw new InvalidParameterException();
    } catch(Exception ex) {
      throw new ResourceNotFoundException();
    }
  }



  /**
   * Creates new book instance
   * curl -X POST -i -H "Content-Type: application/json" --data
   * '{"bookState":"New Book", "bookAvailability":"AVAILABLE", "version":"1st Edition"}'
   * http://localhost:8080/pa165/rest/bookinstance/create
   *
   * @param bookInstance book instance to be created
   * @throws ResourceAlreadyExists
   * @return BookInstanceDTO
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public final BookInstanceDTO createBookInstance(@RequestBody BookInstanceCreateDTO bookInstance) throws Exception {
    log.debug("REST createBookInstance()");

    try {
      Long id = bookInstanceFacade.createBookInstance(bookInstance);
      return bookInstanceFacade.findById(id);
    } catch (Exception ex) {
      throw new ResourceAlreadyExists();
    }
  }

  /**
   * Modifies the state of a book instance
   * curl -X PUT -i -H "Content-Type: application/json" --data
   * '{"bookState":"New State"}'
   * http://localhost:8080/pa165/rest/bookinstance/1
   *
   * @param id id of a book instance to be modified
   * @param newStateDTO {@link BookInstanceNewStateDTO} new state
   * @return modified BookInstanceDTO
   * @throws InvalidParameterException
   */
  @RequestMapping(value = "/{id}/newstate", method = RequestMethod.PUT,
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE)
  public final BookInstanceDTO changeBookState(@PathVariable("id") Long id, @RequestBody BookInstanceNewStateDTO newStateDTO) throws Exception {
    log.debug("REST changeBookState({})", id);
    newStateDTO.setId(id);
    bookInstanceFacade.changeBookState(newStateDTO);
    return bookInstanceFacade.findById(id);
  }

  /**
   * Modifies the availability of a book instance
   * curl -X PUT -i -H "Content-Type: application/json" --data
   * '"REMOVED"'
   * http://localhost:8080/pa165/rest/bookinstance/1
   *
   * @param id id of a book instance to be modified
   * @param newAvailability {@link BookInstanceAvailability} new availability
   * @return updated BookInstanceDTO
   */
  @RequestMapping(value = "/{id}/newavailability", method = RequestMethod.PUT,
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE)
  public final BookInstanceDTO changeBookAvailability(@PathVariable("id") Long id, @RequestBody BookInstanceAvailability newAvailability) {
    log.debug("REST changeBookAvailability({id})", id);
    bookInstanceFacade.changeBookAvailability(id, newAvailability);
    return bookInstanceFacade.findById(id);
  }
}
