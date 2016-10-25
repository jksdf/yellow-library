/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.yellowlibrary.backend.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author cokin
 */
public class Loan {
    private Date dateFrom;
    private Date returnDate;
    private int loanLength;
    private String loanState;
    private User user;
    private BookInstance book;
    private BigDecimal fine;
}
