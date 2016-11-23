package cz.muni.fi.yellowlibrary.pa165.service;

import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Jozef Zivcic
 */
public class BeanMappingServiceImpl implements BeanMappingService {

  @Inject
  private Mapper mapper;

  @Override
  public <T> List<T> mapTo(Collection<?> source, Class<T> mapToClass) {
    List<T> res = new ArrayList<>();
    for (Object obj : source)
      res.add(mapTo(obj, mapToClass));
    return res;
  }

  @Override
  public <T> T mapTo(Object source, Class<T> mapToClass) {
    return mapper.map(source, mapToClass);
  }

  @Override
  public Mapper getMapper() {
    return mapper;
  }
}
