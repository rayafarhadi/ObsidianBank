package com.bank.user;

import com.bank.bank.Bank;
import com.bank.generics.Roles;
import java.io.Serializable;

/**
 * A class that represents and admin at a bank
 */
public class Admin extends User implements Serializable{

  private static final long serialVersionUID = -773386277704796119L;

  /**
   * Constructs an admin
   *
   * @param id user's id
   * @param name user's name
   * @param age user's age
   * @param address user's address
   */
  public Admin(int id, String name, int age, String address) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.roleId = Bank.rolesMap.get(Roles.ADMIN).getId();
    this.authenticated = false;
  }

  /**
   * Constructs an admin, and sets it's authentication status
   *
   * @param id user's id
   * @param name user's name
   * @param age user's age
   * @param address user's address
   */
  public Admin(int id, String name, int age, String address, boolean authenticated) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.address = address;
    this.roleId = Bank.rolesMap.get(Roles.ADMIN).getId();
    this.authenticated = authenticated;
  }
}
