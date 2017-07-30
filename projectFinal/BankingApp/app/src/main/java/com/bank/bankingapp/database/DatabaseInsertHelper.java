package com.bank.bankingapp.database;

import android.content.Context;

import java.math.BigDecimal;

/**
 * Helper class for DatabaseInserter that detaches the user from the database by removing the need
 * for connection to be passed in through the users side.
 */
public class DatabaseInsertHelper {

    private DatabaseDriverA db;
    private DatabaseValueCheckerHelper checker = new DatabaseValueCheckerHelper();
    private Context context;

    public DatabaseInsertHelper(Context context) {
        db = new DatabaseDriverA(context);
        this.context = context;
    }

    /**
     * Insert a new account into account table.
     *
     * @param name    the name of the account.
     * @param balance the balance currently in account.
     * @param typeId  the id of the type of the account.
     * @return accountId of inserted account, -1 otherwise
     */
    public long insertAccount(String name, BigDecimal balance, long typeId) {

        // Check if input is valid
        boolean valid =
                (name != null && !name.equals("")) && checker.accountTypeIdChecker((int)typeId);

        long result;

        // If the input is correct then insert and return the account id.
        // Otherwise return -1
        if (valid) {
            result = db.insertAccount(name,
                    checker.balanceRounding(balance), (int)typeId);
        } else {
            result = -1;
        }

        return result;
    }

    /**
     * insert an accountType into the accountType table.
     *
     * @param name         the name of the type of account.
     * @param interestRate the interest rate for this type of account.
     * @return true if successful, false otherwise.
     */
    public long insertAccountType(String name, BigDecimal interestRate) {

        // Check if input is vahlid
        boolean valid = checker.accountTypeChecker(name)
                && checker.interestRateChecker(interestRate);

        long result;

        // If the input is valid then insert the account type and return whether the addition was
        // successful.
        // Otherwise return false
        if (valid) {
            result = db.insertAccountType(name, interestRate);
        } else {
            result = -1;
        }
        return result;
    }

    /**
     * Use this to insert a new user.
     *
     * @param name     the user's name.
     * @param age      the user's age.
     * @param address  the user's address.
     * @param roleId   the user's role.
     * @param password the user's password (not hashed).
     * @return the account id if successful, -1 otherwise
     */
    public long insertNewUser(String name, int age, String address, int roleId, String password) {

        // Check if input is valid
        boolean valid = (name != null && !name.equals("")) && (age > 0)
                && checker.addressLengthChecker(address)
                && checker.roleIdChecker(roleId);

        long result;

        // If the input is valid then insert the new user and return the user id.
        // Otherwise return -1
        if (valid) {
            result = db.insertNewUser(name, age, address, roleId, password);
        } else {
            result = -1;
        }

        return result;
    }

    /**
     * Use this to insert new roles into the database.
     *
     * @param role the new role to be added.
     * @return true if successful, false otherwise.
     */
    public long insertRole(String role) {

        // Check if input is valid
        boolean valid = checker.roleTypeChecker(role);


        long result;

        // If the input is valid then insure the role and return whether the insertion was successful.
        // Otherwise return false
        if (valid) {
            result = db.insertRole(role);
        } else {
            result = -1;
        }

        return result;
    }

    /**
     * insert a user and account relationship.
     *
     * @param userId    the id of the user.
     * @param accountId the id of the account.
     * @return true if successful, false otherwise.
     */
    public long insertUserAccount(int userId, int accountId) {

        // Check if input is valid

        boolean valid = !checker.userHasAccountChecker(userId, accountId, context);

        long result;

        // If the input is valid then insert the user account and return whether the insertion was
        // successful.
        // Otherwise return false
        if (valid) {
            result = db.insertUserAccount(userId, accountId);
        } else {
            result = -1;
        }

        return result;
    }

    /**
     * Inserts a message into the database for a specific user. Returns message id on success and -1 on failure.
     *
     * @param userId  if of the user.
     * @param message must be less then 512 characters.
     * @return the message id of the message, -1 if message could not be inserted.
     */
    public long insertMessage(int userId, String message) {

        long messageId;
        if (message.length() > 512) {
            System.out.println("Message was too long to be inserted into the database.");
            return -1;
        }
        try {
            messageId = db.insertMessage(userId, message);
        } catch (Exception e) {
            System.out.println("Message failed to be inserted into database.");
            return -1;
        }
        return messageId;
    }

}
