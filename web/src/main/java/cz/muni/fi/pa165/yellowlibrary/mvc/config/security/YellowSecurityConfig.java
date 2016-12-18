package cz.muni.fi.pa165.yellowlibrary.mvc.config.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;

/**
 * @author Jozef Zivcic
 */
@Configuration
@EnableWebSecurity
public class YellowSecurityConfig extends WebSecurityConfigurerAdapter {

  @Inject
  private UserFacade userFacade;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/home", "/css/own.css", "/favicon.ico", "/not_found").permitAll()
        .antMatchers("/bookinstance", "/bookinstance/", "/bookinstance/list").permitAll()
        .antMatchers("/book/edit", "/book/create").hasAnyRole("EMPLOYEE")
        .antMatchers("/book", "/book/", "/book/list", "/book/*").permitAll()
        .antMatchers("/bookinstance/**").hasAnyRole("EMPLOYEE")
        .antMatchers("/department", "/department/list").permitAll()
        .antMatchers("/department/create", "/department/edit").hasAnyRole("EMPLOYEE")
        .antMatchers("/user/").hasAnyRole("EMPLOYEE", "CUSTOMER")
        .antMatchers("/user/**").hasAnyRole("EMPLOYEE")
        .antMatchers("/*").hasAnyRole("EMPLOYEE", "CUSTOMER")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .failureUrl("/login?error=invalid_attempt")
        .usernameParameter("user_login").passwordParameter("user_password")
        .permitAll()
        .and()
        .logout()
        .permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/access_denied")
        .and()
        .csrf().disable();
  }

  @Inject
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    List<UserDTO> users = userFacade.findAllUsers();
    PasswordEncoder passwordEncoder = new PasswordEncoderImpl();

    for (UserDTO userDTO : users) {
      if (userDTO.isEmployee()) {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser(userDTO.getLogin())
            .password(userDTO.getPasswordHash()).roles("EMPLOYEE");
      }
      if (userDTO.isCustomer()) {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser(userDTO.getLogin())
            .password(userDTO.getPasswordHash()).roles("CUSTOMER");
      }
    }
  }
}
