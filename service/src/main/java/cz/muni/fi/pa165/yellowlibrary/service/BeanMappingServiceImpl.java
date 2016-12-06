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

import cz.muni.fi.pa165.yellowlibrary.backend.enums.BookAvailability;

/**
 * @author Jozef Zivcic
 */
@Service
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
    if (source != null && mapToClass != null && source.getClass().isEnum() && mapToClass.isEnum()) {
      return (T)this.getEnum(source, mapToClass);
    }

    return mapper.map(source, mapToClass);
  }

  @Override
  public Mapper getMapper() {
    return mapper;
  }

  private Object getEnum(Object source, Class<?> mapToClass){
      Method [] ms = mapToClass.getMethods();
      for(Method m : ms) {
        if (m.getName().equalsIgnoreCase("valueOf")) {
          try {
            return m.invoke(mapToClass.getClass(), source.toString());
          } catch (IllegalArgumentException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          }
        }
      }

    throw new MappingException("Enum cannot be converted to destination type!");
  }

}
