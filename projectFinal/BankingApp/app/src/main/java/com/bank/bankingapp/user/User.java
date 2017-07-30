package com.bank.bankingapp.user;

import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.security.PasswordHelpers;

import java.sql.SQLException;

public abstract class User {

  protected int id;
  protected String name;
  protected int age;
  protected String address;
  protected int roleId;
  protected boolean authenticated = false;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getRoleId() {
    return roleId;
  }

  public String getAddress() {
    return address;
  }

  /**
   * Checks if the given password matches the one stored on the database
   *
   * @param password password to check
   * @return true if correct, false if not
   * @throws SQLException if connection fails
   */
  public final boolean authenticate(String password) throws SQLException {
    String dbPass = DatabaseSelectHelper.getPassword(id);
    authenticated = PasswordHelpers.comparePassword(dbPass, password);
    return authenticated;
  }
}
