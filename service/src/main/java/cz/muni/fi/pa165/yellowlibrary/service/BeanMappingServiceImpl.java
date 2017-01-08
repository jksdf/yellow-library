package cz.muni.fi.pa165.yellowlibrary.service;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Jozef Zivcic
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

  @Inject
  private Mapper mapper;

  @Override
  public <T> List<T> mapTo(Collection<?> source, Class<T> mapToClass) {
    if (source == null) {
      return null;
    }
    List<T> res = new ArrayList<>();
    for (Object obj : source)
      res.add(mapTo(obj, mapToClass));
    return res;
  }

  @Override
  public <T> T mapTo(Object source, Class<T> mapToClass) {
    checkNotNull(mapToClass);
    if (source == null) {
      return null;
    }
    if (source.getClass().isEnum() && mapToClass.isEnum()) {
      return (T) this.getEnum(source, (Class<? extends Enum>) mapToClass);
    }
    return mapper.map(source, mapToClass);
  }

  @Override
  public Mapper getMapper() {
    return mapper;
  }

  private <T extends Enum> T getEnum(Object source, Class<T> mapToClass) {
    Method valueOf;
    try {
      valueOf = mapToClass.getMethod("valueOf", String.class);
    } catch (NoSuchMethodException e) {
      throw new AssertionError("Enums have valueOf(String)", e);
    }
    try {
      return (T) valueOf.invoke(mapToClass, source.toString());
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new MappingException("Unable to find equivalent enum value");
    }
  }

}
