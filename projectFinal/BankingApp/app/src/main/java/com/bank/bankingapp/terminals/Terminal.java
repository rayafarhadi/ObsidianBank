package com.bank.bankingapp.terminals;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.database.DatabaseSelectHelper;
import com.bank.bankingapp.database.PasswordHelpers;
import com.bank.bankingapp.user.User;

public class Terminal {

    protected User currentUser;
    protected boolean authenticated;
    protected int designatedUserId;

    /**
     * Authenticates user with password, and verifies user role Id
     *
     * @param userId   of user
     * @param password of user
     * @return if the user was authenticated or not. If the user was already authenticated still
     * returns true.
     */
    public boolean logIn(int userId, String password) {
        currentUser = DatabaseSelectHelper.getUserDetails(userId);
        authenticated = (PasswordHelpers
                .comparePassword(DatabaseSelectHelper.getPassword(userId), password)) && (
                currentUser.getRoleId() == designatedUserId);
        return this.authenticated;
    }

    /**
     * Returns the sum of all account balances a given user has with the bank.
     *
     * @param userId id of the user
     * @return the sum of all balances
     */
    public BigDecimal addBalances(int userId) {
        // Make sure terminal is authenticated
        if (!authenticated) {
            return null;
        }

        BigDecimal sum = new BigDecimal(0);
        User customer = DatabaseSelectHelper.getUserDetails(userId);

        // check that the customer is a customer, and makes sure it returns null if the account doesn't exist
        if ((customer == null) || (customer.getRoleId() != Bank.rolesMap.get(Roles.CUSTOMER).getId())) {
            return null;
        }
        for (Account account : ((Customer) customer).getAccounts()) {
            sum = sum.add(account.getBalance());
        }
        return sum;
    }

    /**
     * Returns the sum of all account balances.
     *
     * @return the sum of all balances
     */
    public BigDecimal addAllBalances() {
        BigDecimal sum = new BigDecimal(0);
        int accountId = 1;
        // Make sure terminal is authenticated
        if (!authenticated) {
            return null;
        }
        Account account = DatabaseSelectHelper.getAccountDetails(accountId);
        while (account != null) {
            sum = sum.add(account.getBalance());
            account = DatabaseSelectHelper.getAccountDetails(accountId);
            accountId++;
        }
        return sum;
    }

    /**
     * Gets an array list of current messages.
     *
     * @return list of messages.
     * @throws SQLException
     */
    public ArrayList<Message> viewAllMessages() {
        if (!authenticated) {
            System.out.println("User not authenticated!");
            return null;
        }
        try {
            return DatabaseSelectHelper.getAllMessages(currentUser.getId());
        } catch (SQLException e) {
            System.out.println("Current user's user id does not exist in the database.");
            return null;
        }
    }
}
