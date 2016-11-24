package cz.muni.fi.pa165.yellowlibrary.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Jozef Zivcic
 */
public interface BeanMappingService {

  <T> List<T> mapTo(Collection<?> source, Class<T> mapToClass);

  <T> T mapTo(Object source, Class<T> mapToClass);

  Mapper getMapper();
}
