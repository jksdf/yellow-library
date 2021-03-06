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
 * For testing methods on this controller see file /rest/readme.txt
 *
 * @author Norbert Slivka
 */
@RestController
public class MainController {

  private static final Logger logger = Logger.getLogger(MainController.class);

  /**
   * Main entry of REST API
   *
   * @return resource to uri mapping
   */
  @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public final Map<String, String> getResources() {

    ImmutableMap.Builder<String, String> resourcesMap = ImmutableMap.builder();

    resourcesMap.put("book_uri", ApiUris.ROOT_URI_BOOK);
    resourcesMap.put("user_uri", ApiUris.ROOT_URI_USER);
    resourcesMap.put("department_uri", ApiUris.ROOT_URI_DEPARTMENT);
    resourcesMap.put("loan_uri", ApiUris.ROOT_URI_LOAN);
    resourcesMap.put("bookinstance_uri", ApiUris.ROOT_URI_BOOKINSTANCES);

    return resourcesMap.build();
  }
}
