package cz.muni.fi.pa165.yellowlibrary.mvc.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.Filter;

/**
 * @author Jozef Zivcic
 */
public class YellowStartInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[]{YellowSpringMvcConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }

  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("utf-8");
    return new Filter[]{encodingFilter};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return null;
  }
}
