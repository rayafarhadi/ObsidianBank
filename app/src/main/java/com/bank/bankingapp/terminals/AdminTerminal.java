package com.bank.bankingapp.terminals;

import android.content.Context;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.database.DatabaseInsertHelper;
import com.bank.bankingapp.database.DatabaseSelectHelper;
import com.bank.bankingapp.database.DatabaseUpdateHelper;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.user.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AdminTerminal extends Terminal {

    public AdminTerminal() {
        designatedUserId = Bank.rolesMap.get(Roles.ADMIN).getId();
    }

    /**
     * Adds a new admin to the database
     *
     * @param userName admin's userName
     * @param age      admin's age
     * @param address  admin's address
     * @param typeId   admin's typeId
     * @param password admin's password
     * @return the new admin's id if successful, -1 if not
     */
    public int createUser(String userName, int age, String address, int typeId, String password, Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return (int) db.insertNewUser(userName, age, address, typeId, password);
    }

    /**
     * Returns a list of all users of type roleId if the terminal is authenticated
     *
     * @return a list of all users of type roleId, or null if not authenticated
     * @throws SQLException if connection error
     */
    public List<User> getUserType(int roleId) {
        // make sure admin is authenticated
        if (!authenticated) { // TODO: use helper method to make sure 1 <= roleId <= 3
            return null;
        }
        List<User> users = new ArrayList<>();
        int id = 1;
        // get all users
        User currUser = DatabaseSelectHelper.getUserDetails(id);
        while (currUser != null) {
            if ((currUser.getRoleId() == roleId)) {
                users.add(currUser);
            }
            id++;
            currUser = DatabaseSelectHelper.getUserDetails(id);
        }
        return users;
    }

    /**
     * Serializes the Users and Accounts in the database to be backed up onto a database.ser file
     *
     * @throws IOException
     * @throws SQLException
     */
    public void serializeDatabase() throws IOException {
        FileOutputStream file = new FileOutputStream("database.ser");
        ObjectOutputStream out = new ObjectOutputStream(file);

        // get all users
        List<User> users = new ArrayList<>();
        int id = 1;

        User currUser = DatabaseSelectHelper.getUserDetails(id);
        while (currUser != null) {
            users.add(currUser);
            id++;
            currUser = DatabaseSelectHelper.getUserDetails(id);
        }

        // get all accounts
        List<Account> accounts = new ArrayList<>();

        for (User user : users) {
            if (user.getRoleId() == Bank.rolesMap.get(Roles.CUSTOMER).getId()) {
                for (Integer accountId : DatabaseSelectHelper.getAccountIds(user.getId())) {
                    accounts.add(DatabaseSelectHelper.getAccountDetails(accountId));
                }
            }
        }

        List<String> passwords = new ArrayList<>();

        for (User user : users) {
            passwords.add(DatabaseSelectHelper.getPassword(user.getId()));
        }

        out.writeObject(users);
        out.writeObject(accounts);
        out.writeObject(passwords);

        out.close();
        file.close();

    }

    public void deserializeDatabase() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("database.ser");
        ObjectInputStream in = new ObjectInputStream(file);

        List<User> users = (ArrayList<User>) in.readObject();
        List<Account> accounts = (ArrayList<Account>) in.readObject();
        List<String> passwords = (ArrayList<String>) in.readObject();

        int password_index = 0;
        for (User user : users) {
            DatabaseInsertHelper.insertNewUser(user.getName(), user.getAge(), user.getAddress(),
                    user.getRoleId(), "Temp pass");
            DatabaseUpdateHelper.updateUserPassword(passwords.get(password_index), user.getId());
            password_index++;
        }


        in.close();
        file.close();
    }
}
