package cz.muni.fi.pa165.yellowlibrary.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.LoanFacade;
import cz.muni.fi.pa165.yellowlibrary.rest.ApiUris;
import cz.muni.fi.pa165.yellowlibrary.rest.exceptions.ResourceNotFoundException;

/**
 * For testing methods on this controller see file /rest/readme.txt
 *
 * @author cokinova
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_LOAN)
public class LoanController {

  final static Logger log = LoggerFactory.getLogger(LoanController.class);

  @Inject
  private LoanFacade loanFacade;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final LoanDTO findById(@PathVariable("id") Long id) throws Exception {
    log.debug("REST getLoan({})", id);
    try {
      return loanFacade.findById(id);
    } catch(Exception ex) {
      throw new ResourceNotFoundException();
    }
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public final LoanDTO createLoan(@RequestBody LoanCreateDTO loanCreateDTO) throws Exception {
    log.debug("REST createLoan()");
    try {
      Long id = loanFacade.create(loanCreateDTO);
      return loanFacade.findById(id);
    } catch (Exception ex) {
      throw new ResourceNotFoundException();
    }
  }

  @RequestMapping(value = "/recalculateFines", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final void recalculateFines() throws Exception {
    log.debug("REST recalculateFines()");
    try {
      loanFacade.CalculateFinesForExpiredLoans();
    } catch(Exception ex) {
      throw new ResourceNotFoundException();
    }
  }

}
