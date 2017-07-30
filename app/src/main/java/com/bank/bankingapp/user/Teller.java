package com.bank.bankingapp.user;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.Roles;

import java.io.Serializable;

public class Teller extends User implements Serializable {

    private static final long serialVersionUID = 5775504761772512942L;

    /**
     * Constructs a teller
     *
     * @param id      user's id
     * @param name    user's name
     * @param age     user's age
     * @param address user's address
     */
    public Teller(int id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.roleId = Bank.rolesMap.get(Roles.TELLER).getId();
        this.authenticated = false;
    }

    /**
     * Constructs a teller, and sets it's authentication status
     *
     * @param id      user's id
     * @param name    user's name
     * @param age     user's age
     * @param address user's address
     */
    public Teller(int id, String name, int age, String address, boolean authenticated) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.roleId = Bank.rolesMap.get(Roles.TELLER).getId();
        this.authenticated = authenticated;
    }
}
