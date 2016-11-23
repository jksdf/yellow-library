package cz.muni.fi.yellowlibrary.pa165.service.configuration;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.backend.LibraryApplicationContext;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.yellowlibrary.pa165.service.UserServiceImpl;
import cz.muni.fi.yellowlibrary.pa165.service.facade.UserFacadeImpl;

/**
 * @author Jozef Zivcic
 */
@Configuration
@Import(LibraryApplicationContext.class)
@ComponentScan(basePackageClasses = {UserServiceImpl.class, UserFacadeImpl.class})
public class ServiceConfiguration {

  @Bean
  public Mapper dozer(){
    DozerBeanMapper dozer = new DozerBeanMapper();
    dozer.addMapping(new DozerCustomConfig());
    return dozer;
  }

  public class DozerCustomConfig extends BeanMappingBuilder {
    @Override
    protected void configure() {
      mapping(User.class, UserDTO.class);
    }
  }
}
