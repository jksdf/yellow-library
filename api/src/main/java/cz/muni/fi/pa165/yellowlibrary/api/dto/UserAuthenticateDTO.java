package cz.muni.fi.pa165.yellowlibrary.api.dto;

import javax.validation.constraints.Size;

/**
 * This object is used for authentication of users.
 *
 * @author Jozef Zivcic
 */
public class UserAuthenticateDTO {

  @Size(min = 1)
  private String login;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }
}
