/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.yellowlibrary.backend.entity;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cokin
 */
public class User {
    //Name, Address, Loans, UserType(enum), totalFines
    private String name;
    private String address;
    private BigDecimal totalFines;
    private List<Loan> loans;
    private UserType userType;
}
