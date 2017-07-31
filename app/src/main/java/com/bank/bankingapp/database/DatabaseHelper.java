package com.bank.bankingapp.database;

import android.content.Context;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.user.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper implements Serializable {

    private static final long serialVersionUID = -7626359737075876388L;

    private DatabaseInsertHelper inserter;
    private DatabaseSelectHelper selector;
    private DatabaseUpdateHelper updater;

    public DatabaseHelper(Context context) {
        inserter = new DatabaseInsertHelper(context);
        selector = new DatabaseSelectHelper(context);
        updater = new DatabaseUpdateHelper(context);
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
        return inserter.insertAccount(name, balance, typeId);
    }

    /**
     * insert an accountType into the accountType table.
     *
     * @param name         the name of the type of account.
     * @param interestRate the interest rate for this type of account.
     * @return true if successful, false otherwise.
     */
    public long insertAccountType(String name, BigDecimal interestRate) {
        return inserter.insertAccountType(name, interestRate);
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
        return inserter.insertNewUser(name, age, address, roleId, password);
    }

    /**
     * Use this to insert new roles into the database.
     *
     * @param role the new role to be added.
     * @return true if successful, false otherwise.
     */
    public long insertRole(String role) {
        return inserter.insertRole(role);
    }

    /**
     * insert a user and account relationship.
     *
     * @param userId    the id of the user.
     * @param accountId the id of the account.
     * @return true if successful, false otherwise.
     */
    public long insertUserAccount(int userId, int accountId) {
        return inserter.insertUserAccount(userId, accountId);
    }

    /**
     * Inserts a message into the database for a specific user. Returns message id on success and -1 on failure.
     *
     * @param userId  if of the user.
     * @param message must be less then 512 characters.
     * @return the message id of the message, -1 if message could not be inserted.
     */
    public long insertMessage(int userId, String message) {
        return inserter.insertMessage(userId, message);
    }

    /**
     * Verify the id, then get the role with that id
     *
     * @param id the id of the role
     * @return a String containing the role.
     */
    public String getRole(int id) {
        return selector.getRole(id);
    }

    /**
     * Get the hashed version of user's password.
     *
     * @param userId the user's id.
     * @return the hashed password to be checked against given password.
     */
    public String getPassword(int userId) {
        return selector.getPassword(userId);
    }

    /**
     * Returns a new user that is a clone of the one in the database with id userId
     *
     * @param userId id of user
     * @return the user
     */
    public User getUserDetails(int userId) {
        return selector.getUserDetails(userId);
    }

    /**
     * Returns a list of all accounts associated with the user with id userId
     *
     * @param userId id of the user
     * @return list of accounts
     */
    public List<Integer> getAccountIds(int userId) {
        return selector.getAccountIds(userId);
    }

    /**
     * returns the account with id accountId
     *
     * @param accountId account's id
     * @return the new account
     */
    public Account getAccountDetails(int accountId) {
        return selector.getAccountDetails(accountId);
    }

    /**
     * return the balance in the account.
     *
     * @param accountId the account to check.
     * @return the balance
     */
    public BigDecimal getBalance(int accountId) {
        return selector.getBalance(accountId);
    }

    /**
     * Get the interest rate for an account.
     *
     * @param accountType the type for the account.
     * @return the interest rate if successful, -1 if not
     */
    public BigDecimal getInterestRate(int accountType) {
        return selector.getInterestRate(accountType);
    }

    /**
     * Returns a list of all the account Type Ids in the table
     *
     * @return a list of account type ids
     */
    public List<Integer> getAccountTypesIds() {
        return selector.getAccountTypesIds();
    }

    /**
     * Return the account type name given an accountTypeId.
     *
     * @param accountTypeId the id of the account type.
     * @return The name of the account type.
     */
    public String getAccountTypeName(int accountTypeId) {
        return selector.getAccountTypeName(accountTypeId);
    }

    /**
     * Returns a list of all roles in the table
     *
     * @return a list of roles
     * @throws SQLException if connection fails
     */
    public List<Integer> getRoles() {
        return selector.getRoles();
    }

    /**
     * Return the account type name given an accountTypeId.
     *
     * @param accountId the id of the account type.
     * @return The name of the account type.
     */
    public int getAccountType(int accountId) {
        return selector.getAccountType(accountId);
    }

    /**
     * get the role of the given user.
     *
     * @param userId the id of the user.
     * @return the roleId for the user.
     */
    public int getUserRole(int userId) {
        return selector.getUserRole(userId);
    }

    /**
     * Get all messages currently available to a user.
     *
     * @param userId the user whose messages are being retrieved.
     * @return a result set containing all messages.
     */
    public ArrayList<Message> getAllMessages(int userId) {
        return selector.getAllMessages(userId);
    }

    /**
     * Gets a specific message from the database.
     *
     * @param messageId of the message
     * @return the message
     */
    public Message getSpecificMessage(int messageId) {
        return selector.getSpecificMessage(messageId);
    }

    /**
     * Update the role name of a given role in the role table.
     *
     * @param name the new name of the role.
     * @param id   the current ID of the role.
     * @return true if successful, false otherwise.
     */
    public boolean updateRoleName(String name, int id) {
        return updater.updateRoleName(name, id);
    }


    /**
     * Update the user's name.
     *
     * @param name the new name
     * @param id   the current id
     * @return true if it works, false otherwise.
     */
    public boolean updateUserName(String name, int id) {
        return updater.updateUserName(name, id);
    }


    /**
     * Use this to update the user's age.
     *
     * @param age the new age.
     * @param id  the current id
     * @return true if it succeeds, false otherwise.
     */
    public boolean updateUserAge(int age, int id) {
        return updater.updateUserAge(age, id);
    }

    /**
     * update the role of the user.
     *
     * @param roleId the new role.
     * @param id     the current id.
     * @return true if successful, false otherwise.
     */
    public boolean updateUserRole(int roleId, int id) {
        return updater.updateUserRole(roleId, id);
    }


    /**
     * Use this to update user's address.
     *
     * @param address the new address.
     * @param id      the current id.
     * @return true if successful, false otherwise.
     */
    public boolean updateUserAddress(String address, int id) {
        return updateUserAddress(address, id);
    }


    /**
     * update the name of the account.
     *
     * @param name the new name for the account.
     * @param id   the id of the account to be changed.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountName(String name, int id) {
        return updater.updateAccountName(name, id);
    }

    /**
     * update the account balance.
     *
     * @param balance the new balance for the account.
     * @param id      the id of the account.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountBalance(BigDecimal balance, int id) {
        return updater.updateAccountBalance(balance, id);
    }


    /**
     * update the type of the account.
     *
     * @param typeId the new type for the account.
     * @param id     the id of the account to be updated.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountType(int typeId, int id) {
        return updater.updateAccountType(typeId, id);
    }


    /**
     * update the name of an accountType.
     *
     * @param name the new name to be given.
     * @param id   the id of the accountType.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountTypeName(String name, int id) {
        return updater.updateAccountTypeName(name, id);
    }


    /**
     * update the interest rate for this account type.
     *
     * @param interestRate the interest rate to be updated to.
     * @param id           the id of the accountType.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) {
        return updater.updateAccountTypeInterestRate(interestRate, id);
    }

    /**
     * Update the hashed password for user in database
     *
     * @param password The hashed password
     * @param userId   The users id
     * @return true if successful, false otherwise
     */
    public boolean updateUserPassword(String password, int userId) {
        return updater.updateUserPassword(password, userId);
    }

    /**
     * Updates the a message with message id to viewed in the database.
     *
     * @param messageId of message
     * @return of the update was successful or not.
     */
    public boolean updateUserMessageState(int messageId) {
        return updater.updateUserMessageState(messageId);
    }


}
