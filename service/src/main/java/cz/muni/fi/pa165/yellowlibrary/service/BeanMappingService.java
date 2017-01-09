package cz.muni.fi.pa165.yellowlibrary.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Jozef Zivcic
 */
public interface BeanMappingService {

  /**
   * Maps the collection of source type to a collection of the target type.
   * Maps {@code null} to {@code null}.
   *
   * @param source     Collection of source type data.
   * @param mapToClass target type
   */
  <T> List<T> mapTo(Collection<?> source, Class<T> mapToClass);

  /**
   * Maps the source object to the target type.
   * Maps {@code null} to {@code null}.
   * Can handle {@code enum} conversion using {@code valueOf}.
   */
  <T> T mapTo(Object source, Class<T> mapToClass);

  /**
   * Returns underlying mapper.
   */
  Mapper getMapper();
}
