package com.bank.bankingapp.user;

import android.content.Context;

import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.database.PasswordHelpers;

import java.io.Serializable;

public abstract class User implements Serializable {

    private static final long serialVersionUID = -6186838150151744131L;
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
     */
    public final boolean authenticate(String password, Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        String dbPass = db.getPassword(id);
        authenticated = PasswordHelpers.comparePassword(dbPass, password);
        return authenticated;
    }
}
