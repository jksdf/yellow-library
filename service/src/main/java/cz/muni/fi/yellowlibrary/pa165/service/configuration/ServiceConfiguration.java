package cz.muni.fi.yellowlibrary.pa165.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;
import cz.muni.fi.pa165.yellowlibrary.backend.LibraryApplicationContext;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.yellowlibrary.pa165.service.UserServiceImpl;

/**
 * @author Jozef Zivcic
 */
@Configuration
@Import(LibraryApplicationContext.class)
@ComponentScan(basePackageClasses = {UserServiceImpl.class, UserFacade.class})
public class ServiceConfiguration {

}
