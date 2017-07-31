package com.bank.bankingapp.database;

import android.content.Context;
import android.database.Cursor;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.account.AccountFactory;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.user.User;
import com.bank.bankingapp.user.UserFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSelectHelper implements Serializable {

    private static final long serialVersionUID = -6698968334598438233L;

    private DatabaseDriverA db;
    private Context context;
    private DatabaseValueCheckerHelper checker = new DatabaseValueCheckerHelper();

    public DatabaseSelectHelper(Context context) {
        db = new DatabaseDriverA(context);
        this.context = context;
    }

    /**
     * Verify the id, then get the role with that id
     *
     * @param id the id of the role
     * @return a String containing the role.
     */
    public String getRole(int id) {
        if (checker.roleIdChecker(id)) {
            String role = db.getRole(id);
            return role;
        }
        return null;
    }

    /**
     * Get the hashed version of user's password.
     *
     * @param userId the user's id.
     * @return the hashed password to be checked against given password.
     */
    public String getPassword(int userId) {
        return db.getPassword(userId);
    }

    /**
     * Returns a new user that is a clone of the one in the database with id userId
     *
     * @param userId id of user
     * @return the user
     */
    public User getUserDetails(int userId) {
        if (userId > 0) {
            Cursor results = db.getUserDetails(userId);
            String name = "";
            int age = 0;
            String address = "";
            int roleId = 0;

            if (results.moveToFirst()) {
                do {
                    name = results.getString(results.getColumnIndex("NAME"));
                    age = results.getInt(results.getColumnIndex("AGE"));
                    address = results.getString(results.getColumnIndex("ADDRESS"));
                    roleId = results.getInt(results.getColumnIndex("ROLEID"));
                } while (results.moveToNext());
            }
            return UserFactory.getUser(roleId, userId, name, age, address, context);
        } else {
            return null;
        }

    }

    /**
     * Returns a list of all accounts associated with the user with id userId
     *
     * @param userId id of the user
     * @return list of accounts
     */
    public List<Integer> getAccountIds(int userId) {
        Cursor results = db.getAccountIds(userId);
        ArrayList<Integer> idList = new ArrayList<>();

        if (results.moveToFirst()) {
            do {
                idList.add(results.getInt(results.getColumnIndex("ACCOUNTS")));
            } while (results.moveToNext());
        }

        return idList;
    }

    /**
     * returns the account with id accountId
     *
     * @param accountId account's id
     * @return the new account
     */
    public Account getAccountDetails(int accountId) {
        if (accountId > 0) {
            Cursor results = db.getAccountDetails(accountId);

            String name = "";
            String balance = "";
            int type = 0;

            if (results.moveToFirst()) {
                name = results.getString(results.getColumnIndex("NAME"));
                balance = results.getString(results.getColumnIndex("BALANCE"));
                type = results.getInt(results.getColumnIndex("TYPE"));
            }

            return AccountFactory.getAccount(type, accountId, name, new BigDecimal(balance));
        } else {
            return null;
        }
    }

    /**
     * return the balance in the account.
     *
     * @param accountId the account to check.
     * @return the balance
     */
    public BigDecimal getBalance(int accountId) {
        return db.getBalance(accountId);
    }

    /**
     * Get the interest rate for an account.
     *
     * @param accountType the type for the account.
     * @return the interest rate if successful, -1 if not
     */
    public BigDecimal getInterestRate(int accountType) {
        if (checker.accountTypeIdChecker(accountType)) {
            return db.getInterestRate(accountType);
        }
        return new BigDecimal(-1);
    }

    /**
     * Returns a list of all the account Type Ids in the table
     *
     * @return a list of account type ids
     */
    public List<Integer> getAccountTypesIds() {
        Cursor results = db.getAccountTypesId();
        List<Integer> ids = new ArrayList<>();

        results.moveToFirst();

        do {
            ids.add(results.getInt(results.getColumnIndex("ID")));
        } while (results.moveToNext());

        return ids;
    }

    /**
     * Return the account type name given an accountTypeId.
     *
     * @param accountTypeId the id of the account type.
     * @return The name of the account type.
     */
    public String getAccountTypeName(int accountTypeId) {
        if (checker.accountTypeIdChecker(accountTypeId)) {
            return db.getAccountTypeName(accountTypeId);
        }
        return null;
    }

    /**
     * Returns a list of all roles in the table
     *
     * @return a list of roles
     * @throws SQLException if connection fails
     */
    public List<Integer> getRoles() {
        Cursor results = db.getRoles();
        List<Integer> ids = new ArrayList<>();

        results.moveToFirst();

        do {
            ids.add(results.getInt(results.getColumnIndex("ID")));
        } while (results.moveToNext());

        return ids;
    }

    /**
     * Return the account type name given an accountTypeId.
     *
     * @param accountId the id of the account type.
     * @return The name of the account type.
     */
    public int getAccountType(int accountId) {
        return db.getAccountType(accountId);
    }

    /**
     * get the role of the given user.
     *
     * @param userId the id of the user.
     * @return the roleId for the user.
     */
    public int getUserRole(int userId) {
        try {
            return db.getUserRole(userId);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Get all messages currently available to a user.
     *
     * @param userId the user whose messages are being retrieved.
     * @return a result set containing all messages.
     */
    public ArrayList<Message> getAllMessages(int userId) {
        ArrayList<Message> messages = new ArrayList<Message>();
        Cursor res = db.getAllMessages(userId);

        res.moveToFirst();

        do {
            int messageId = res.getInt(res.getColumnIndex("ID"));
            String message = res.getString(res.getColumnIndex("MESSAGE"));
            int v = res.getInt(res.getColumnIndex("VIEWED"));

            boolean viewed = false;
            if (v == 0) {
                viewed = false;
            } else if (v == 1) {
                viewed = true;
            }

            Message m = new Message(messageId, userId, message, viewed);
            messages.add(m);
        } while (res.moveToNext());

        return messages;
    }

    /**
     * Gets a specific message from the database.
     *
     * @param messageId of the message
     * @return the message
     */
    public Message getSpecificMessage(int messageId) {
        String s = db.getSpecificMessage(messageId);
        Message m = new Message(messageId, -1, s, false);
        return m;
    }

    public ArrayList<User> getUsers() {
        Cursor results = db.getUsersDetails();

        String name = "";
        int age = 0;
        String address = "";
        int roleId = 0;
        int userId = 0;

        ArrayList<User> users = new ArrayList<>();

        if (results.moveToFirst()) {
            do {
                name = results.getString(results.getColumnIndex("NAME"));
                age = results.getInt(results.getColumnIndex("AGE"));
                address = results.getString(results.getColumnIndex("ADDRESS"));
                roleId = results.getInt(results.getColumnIndex("ROLEID"));
                userId = results.getInt(results.getColumnIndex("ID"));

                users.add(UserFactory.getUser(roleId, userId, name, age, address, context));
            } while (results.moveToNext());
        }
        return users;
    }
}
