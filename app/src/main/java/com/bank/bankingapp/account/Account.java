package com.bank.bankingapp.account;

import com.bank.bankingapp.maps.AccountTypeValues;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;

public abstract class Account implements Serializable {

    private static final long serialVersionUID = -623715467042009427L;

    // initialize all variables of account
    protected transient int id;
    protected String name;
    protected BigDecimal balance;
    protected AccountTypeValues typeInfo;
    protected BigDecimal interestRate;

    public Account() {
        interestRate = typeInfo.getInterest();
        id = typeInfo.getId();
    }

    /**
     * Returns the id of the account
     *
     * @return int id - id of the Account
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the id of the account
     *
     * @param id - id that must be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the account
     *
     * @return String name - name of the account
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the account
     *
     * @param name - string that must be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the balance of the account
     *
     * @return BigDecimal balance- balance of the account
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account
     *
     * @param balance - balance that must be set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Gets the type of the account
     *
     * @return int type - type of the account
     */
    public int getType() {
        return typeInfo.getId();
    }

    /**
     * set interest rate
     *
     * @throws SQLException
     */
    public void findAndSetInterestRate() throws SQLException {
        interestRate = typeInfo.getInterest();
    }

    /**
     * Adds interest to the balance
     *
     * @throws SQLException
     */
    public void addInterest() throws SQLException {
        // create a BigDecimal of 1
        BigDecimal One = new BigDecimal("1");
        // run find and set interest rate
        findAndSetInterestRate();
        // multiply balance by (interest rate +1);
        balance = balance.multiply(One.add(interestRate));
    }

}
