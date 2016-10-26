/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.yellowlibrary.backend.entity;
import cz.muni.fi.pa165.yellowlibrary.backend.enums.UserType;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author cokin
 */
@Entity
@Table(name="LibraryUser") // Name must not be "User" - it is a keyword
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private BigDecimal totalFines;

    @OneToMany
    private List<Loan> loans;

    @Enumerated
    private UserType userType;

    public User() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotalFines(BigDecimal totalFines) {
        this.totalFines = totalFines;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getTotalFines() {
        return totalFines;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public UserType getUserType() {
        return userType;
    }
}
