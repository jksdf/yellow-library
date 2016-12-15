package cz.muni.fi.pa165.yellowlibrary.mvc.config.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;

/**
 * https://spring.io/guides/gs/securing-web/
 * Multiple roles:
 * http://www.concretepage.com/spring/spring-security/spring-mvc-security-in-memory-authentication-example-with-authenticationmanagerbuilder-using-java-configuration
 *
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
        .antMatchers("/", "/home", "/css/own.css", "/favicon.ico").permitAll()
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
