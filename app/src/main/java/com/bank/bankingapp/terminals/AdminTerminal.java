package com.bank.bankingapp.terminals;

import android.content.Context;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.user.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AdminTerminal extends Terminal {

    public AdminTerminal(Context context) {
        super(context);
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
    public int createUser(String userName, int age, String address, int typeId, String password) {
        return (int) db.insertNewUser(userName, age, address, typeId, password);
    }

    /**
     * Returns a list of all users of type roleId if the terminal is authenticated
     *
     * @return a list of all users of type roleId, or null if not authenticated
     * @throws SQLException if connection error
     */
    public List<User> getUserType(int roleId) throws SQLException {
        // make sure admin is authenticated
        if (!authenticated) {
            return null;
        }
        List<User> users = new ArrayList<>();
        int id = 1;
        // get all users
        User currUser = db.getUserDetails(id);
        while (currUser != null) {
            if ((currUser.getRoleId() == roleId)) {
                users.add(currUser);
            }
            id++;
            currUser = db.getUserDetails(id);
        }
        return users;
    }

    /**
     * Gets a specific message from database with messageId without changing its status to viewed.
     *
     * @param messageId id of message
     * @return message
     * @throws SQLException when message id doesnt exists in database.
     */
    public String viewMessageUnseen(int messageId) throws SQLException {
        return db.getSpecificMessage(messageId).getMessage();
    }

    /**
     * Leaves a message for user with userId.
     */
    public boolean leaveMessage(int userId, String message) {
        if (db.getUserDetails(userId) == null) {
            System.out.println("User with user id does not exists in the database.");
            return false;
        }

        db.insertMessage(userId, message);
        return true;
    }

    public boolean promoteTeller(int userId) {
        if (!authenticated) {
            System.out.println("Admin not authenticated.");
            return false;
        }
        if (db.getUserDetails(userId).getRoleId() != Bank.rolesMap.get(Roles.TELLER).getId()) {
            System.out.println("That Id does not belong to a teller.");
            return false;
        }
        if (db.updateUserRole(Bank.rolesMap.get(Roles.ADMIN).getId(), userId)) {
            db.insertMessage(userId,
                    "Your account has been upgraded to an administrator account by: " + currentUser
                            .getName());
            return true;
        } else {
            System.out.println("Promotion was unsuccessful. ");
            return false;
        }
    }
}
