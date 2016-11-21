package cz.muni.fi.pa165.yellowlibrary.api.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by jozef on 21.11.2016.
 */
public class UserDTO {

  @NotNull
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String login;
}
