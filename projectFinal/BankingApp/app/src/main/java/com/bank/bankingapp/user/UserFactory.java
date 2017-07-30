package com.bank.user;

import android.content.Context;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.database.DatabaseSelectHelper;
import com.bank.bankingapp.generics.Roles;

import java.sql.SQLException;

import com.bank.bank.Bank;
import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.generics.Roles;

public class UserFactory {

    /**
     * Builds and returns a user based on the given user role and user information
     *
     * @param roleId  The id of the users role
     * @param userId  The id of the user
     * @param name    The name of the user
     * @param age     The age of the user
     * @param address The address of the user
     * @return The user built based on given information
     */
    public static User getUser(int roleId, int userId, String name, int age, String address, Context context) {

        DatabaseHelper db = new DatabaseHelper(context);

        // If the role id matches to admin then build an admin account
        if (roleId == Bank.rolesMap.get(Roles.ADMIN).getId()) {
            return new Admin(userId, name, age, address);

      // If the role id matches to teller then build an teller account
    } else if (roleId == Bank.rolesMap.get(Roles.TELLER).getId()) {
      return new Teller(userId, name, age, address);

            // If the role id matches to customer then build an customer account
        } else if (roleId == Bank.rolesMap.get(Roles.CUSTOMER).getId()) {
            Customer ret = new Customer(userId, name, age, address);
            for (int accId : db.getAccountIds(userId)) {
                ret.addAccount(db.getAccountDetails(accId));
            }
            return ret;

      // Otherwise return an exception
    } else {
      return null;
    }
  }
}
