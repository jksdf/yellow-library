package cz.muni.fi.pa165.yellowlibrary.api.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * @author cokinova
 */
public class LoanDTO {
  private Long id;

  @NotNull
  private Date dateFrom;

  private Date returnDate;

  @NotNull
  private int loanLength;

  @NotNull
  private String loanState;

  @NotNull
  private UserDTO user;

  @NotNull
  private BookInstanceDTO bookInstance;

  private BigDecimal fine;
}
