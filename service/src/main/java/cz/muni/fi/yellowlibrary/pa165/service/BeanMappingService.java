package cz.muni.fi.yellowlibrary.pa165.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Jozef Zivcic
 */
public interface BeanMappingService {

  public <T> List<T> mapTo(Collection<?> source, Class<T> mapToClass);

  public <T> T mapTo(Object source, Class<T> mapToClass);

  public Mapper getMapper();
}
