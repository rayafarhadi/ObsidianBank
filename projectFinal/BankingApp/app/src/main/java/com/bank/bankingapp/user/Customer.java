package com.bank.bankingapp.user;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.Roles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a bank's customer
 */
public class Customer extends User {

  private List<Account> accounts = new ArrayList<>();


  /**
   * Constructs a customer
   *
   * @param id customer's id
   * @param name customer's name
   * @param age customer's age
   * @param address customer's address
   */
  public Customer(int id, String name, int age, String address) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.roleId = Bank.rolesMap.get(Roles.CUSTOMER).getId();
    this.authenticated = false;
  }

  /**
   * Constructs a customer, and sets its authentication
   *
   * @param id user's id
   * @param name user's name
   * @param age user's age
   * @param address user's address
   */
  public Customer(int id, String name, int age, String address, boolean authenticated) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.roleId = Bank.rolesMap.get(Roles.CUSTOMER).getId();
    this.authenticated = authenticated;
  }

  /**
   * Returns a list the customer's accounts
   *
   * @return a list of accounts
   * @throws SQLException if connection fails
   */
  public List<Account> getAccounts() throws SQLException {
    return accounts;
  }

  /**
   * Add's an account to this customer
   *
   * @param account account to add
   * @throws SQLException if connection fails
   */
  public void addAccount(Account account) throws SQLException {
    this.accounts.add(account);
  }


}
