package cz.muni.fi.pa165.yellowlibrary.rest;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Enables all control methods.
 *
 * @author Norbert Slivka
 */
public class AllowOriginInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler)
      throws Exception {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods",
        "GET, POST, PUT, DELETE, OPTIONS");
    return true;
  }

}
