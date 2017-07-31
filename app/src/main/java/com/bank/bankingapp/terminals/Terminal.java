package com.bank.bankingapp.terminals;

import android.content.Context;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.database.PasswordHelpers;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.user.Customer;
import com.bank.bankingapp.user.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;


public abstract class Terminal implements Serializable {

    private static final long serialVersionUID = 125466293461048941L;
    protected User currentUser;
    protected boolean authenticated;
    protected int designatedUserId;

    protected DatabaseHelper db;

    public Terminal(Context context) {
        db = new DatabaseHelper(context);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Authenticates user with password, and verifies user role Id
     *
     * @param userId   of user
     * @param password of user
     * @return if the user was authenticated or not. If the user was already authenticated still
     * returns true.
     */
    public boolean logIn(int userId, String password) {
        currentUser = db.getUserDetails(userId);
        authenticated = (PasswordHelpers
                .comparePassword(db.getPassword(userId), password)) && (
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
        System.out.println("Running");
        BigDecimal sum = new BigDecimal(0);
        User customer = db.getUserDetails(userId);

        // check that the customer is a customer, and makes sure it returns null if the account doesn't exist
        if ((customer == null) || (customer.getRoleId() != Bank.rolesMap.get(Roles.CUSTOMER).getId())) {
            return null;
        }
        for (Account account : ((Customer) customer).getAccounts()) {
            System.out.println(account.getBalance());
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

        Account account = db.getAccountDetails(accountId);
        while (account != null) {
            sum = sum.add(account.getBalance());
            accountId++;
            account = db.getAccountDetails(accountId);
        }
        return sum;
    }

    /**
     * Gets an array list of current messages.
     *
     * @return list of messages.
     */
    public ArrayList<Message> viewAllMessages() {
        return db.getAllMessages(currentUser.getId());
    }
}
