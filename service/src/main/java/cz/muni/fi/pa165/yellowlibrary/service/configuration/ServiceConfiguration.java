package cz.muni.fi.pa165.yellowlibrary.service.configuration;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import cz.muni.fi.pa165.yellowlibrary.api.dto.BookCreateDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.BookInstanceDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.DepartmentDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.LoanDTO;
import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.backend.LibraryApplicationContext;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Book;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.BookInstance;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Department;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.Loan;
import cz.muni.fi.pa165.yellowlibrary.backend.entity.User;
import cz.muni.fi.pa165.yellowlibrary.service.ServicePackage;
import cz.muni.fi.pa165.yellowlibrary.service.facade.FacadePackage;

/**
 * @author Jozef Zivcic
 */
@Configuration
@Import(LibraryApplicationContext.class)
@EnableScheduling
@ComponentScan(basePackageClasses = {ServicePackage.class, FacadePackage.class})
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
      mapping(Book.class, BookDTO.class);
      mapping(Book.class, BookCreateDTO.class);
      mapping(BookInstance.class, BookInstanceDTO.class);
      mapping(Department.class, DepartmentDTO.class);
      mapping(Loan.class, LoanDTO.class);
    }
  }
}
