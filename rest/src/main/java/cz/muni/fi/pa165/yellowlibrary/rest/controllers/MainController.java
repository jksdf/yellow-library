package cz.muni.fi.pa165.yellowlibrary.rest.controllers;

import com.google.common.collect.ImmutableMap;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import cz.muni.fi.pa165.yellowlibrary.rest.ApiUris;


/**
 * @author Norbert Slivka
 */
@RestController
public class MainController {

  private static final Logger logger = org.apache.log4j.Logger.getLogger(MainController.class);

  /**
   * Main entry of REST API
   *
   * @return resource to uri mapping
   */
  @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final Map<String, String> getResources() {

    ImmutableMap.Builder<String, String> resourcesMap = ImmutableMap.builder();

    resourcesMap.put("book_uri", ApiUris.ROOT_URI_BOOK);

    return resourcesMap.build();
  }
}
