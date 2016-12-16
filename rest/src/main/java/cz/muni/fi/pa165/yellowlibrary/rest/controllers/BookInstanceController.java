package cz.muni.fi.pa165.yellowlibrary.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceNewStateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.BookInstanceFacade;
import cz.muni.fi.pa165.yellowlibrary.rest.ApiUris;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.YellowServiceException;

/**
 * Created by Matej Gallo
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_BOOKINSTANCES)
public class BookInstanceController {

  final static Logger log = LoggerFactory.getLogger(BookInstanceController.class);

  @Inject
  private BookInstanceFacade bookInstanceFacade;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final List<BookInstanceDTO> getBookInstances() {
    log.debug("REST getBookInstances()");
    return bookInstanceFacade.getAllBookInstances();
  }

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

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public final void deleteBookInstance(@PathVariable("id") Long id) throws Exception {
    log.debug("REST deleteBookInstance({})", id);
    try {
      bookInstanceFacade.deleteBookInstance(id);
    } catch(Exception ex) {
      throw new ResourceNotFoundException();
    }
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public final BookInstanceDTO createBookInstance(@RequestBody BookInstanceCreateDTO bookInstance) throws Exception {
    log.debug("REST createBookInstance()");

    try {
      Long id = bookInstanceFacade.createBookInstance(bookInstance);
      return bookInstanceFacade.findById(id);
    } catch (Exception ex) {
      throw new ResourceNotFoundException();
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE)
  public final BookInstanceDTO changeBookState(@PathVariable("id") Long id, @RequestBody BookInstanceNewStateDTO newStateDTO) throws Exception {
    log.debug("REST changeBookState({})", id);

    try {
      newStateDTO.setId(id);
      bookInstanceFacade.changeBookState(newStateDTO);
      return bookInstanceFacade.findById(id);
    } catch (YellowServiceException ex) {
      throw new InvalidParameterException();
    }
  }
/*
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE)
  public final BookInstanceDTO changeBookAvailability(@PathVariable("id") Long id,
                                                      @RequestBody BookInstanceAvailability bookAvailability) throws Exception {
    log.debug("REST changeBookAvailability({id})", id);

    try {
      bookInstanceFacade.changeBookAvailability(id, bookAvailability);
      return bookInstanceFacade.findById(id);
    } catch(YellowServiceException ex) {
      throw new InvalidParameterException();
    }
  }*/

}
