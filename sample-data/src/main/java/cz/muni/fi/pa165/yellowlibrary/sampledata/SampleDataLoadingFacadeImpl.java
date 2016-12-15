package cz.muni.fi.pa165.yellowlibrary.sampledata;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import javax.inject.Inject;

import cz.muni.fi.pa165.yellowlibrary.api.dto.UserDTO;
import cz.muni.fi.pa165.yellowlibrary.api.enums.UserType;
import cz.muni.fi.pa165.yellowlibrary.api.facade.UserFacade;

/**
 * @author Jozef Zivcic
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

  @Inject
  private UserFacade userFacade;

  @Override
  public void loadData() {
    createUsers();
  }

  private void createUsers() {
    UserDTO user1 = getNewUser("John Green", "admin", "admin", "4125 7th Ave, New York, NY 10022, "
        + "USA", UserType.EMPLOYEE);
    userFacade.registerNewUser(user1, user1.getPasswordHash());

    UserDTO user2 = getNewUser("Matt Yellow", "matt", "matt", "Orlando, USA", UserType.CUSTOMER);
    userFacade.registerNewUser(user2, user2.getPasswordHash());

    UserDTO user3 = getNewUser("Simon White", "simon", "simon", "California, USA",
        UserType.CUSTOMER);
    userFacade.registerNewUser(user3, user3.getPasswordHash());
  }

  private UserDTO getNewUser(String name, String login, String password, String address,
                             UserType userType) {
    UserDTO user = new UserDTO();
    user.setName(name);
    user.setLogin(login);
    user.setPasswordHash(password);
    user.setAddress(address);
    user.setUserType(userType);
    user.setTotalFines(BigDecimal.ZERO);
    return user;
  }
}
