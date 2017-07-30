package com.bank.bankingapp.terminals;

import android.content.Context;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.database.DatabaseSelectHelper;
import com.bank.bankingapp.database.DatabaseUpdateHelper;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.IllegalAmountException;
import com.bank.bankingapp.exceptions.InsuffiecintFundsException;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.user.Customer;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ATM extends Terminal {

    public ATM() {
        designatedUserId = Bank.rolesMap.get(Roles.CUSTOMER).getId();
    }

    /**
     * Gets a list of accounts owned by currentUser
     *
     * @return list of accounts owned by currentUser
     */
    public List<Account> listAccounts() throws SQLException {
        // Check to see if customer is authenticated.
        if (!authenticated) {
            return null;
        }
        // Return the list of accounts owned by customer
        return ((Customer) currentUser).getAccounts();
    }

    /**
     * Make a deposit to an account, if deposit is not a valid amount IllegalAmountException thrown.
     * If user is not authenticated throws ConnectionFailedException. If account not owned throws
     * AccountNotOwnedException.
     *
     * @param deposit   amount being deposited
     * @param accountId of account
     * @return if the deposit was successful or not
     */
    public boolean makeDeposit(BigDecimal deposit, int accountId, Context context)
            throws IllegalAmountException, SQLException, ConnectionFailedException {
        DatabaseHelper dbh = new DatabaseHelper(context);
        currentUser = dbh.getUserDetails(currentUser.getId());
        // Check to see if customer is authenticated.
        if (!authenticated) {
            throw new ConnectionFailedException();
        }
        // Check to see if the customer owns the account
        if (!accountOwned((Customer) currentUser, accountId)) {
            return false;
        }
        // Check to make sure that deposit is a positive amount.
        if (deposit.longValue() < 0) {
            throw new IllegalAmountException();
        }
        // Determine what new balance is
        BigDecimal balance = dbh.getBalance(accountId).add(deposit);
        // Update the account balance in the database
        boolean success = dbh.updateAccountBalance(balance, accountId);
        // Return whether the deposit was successful
        return success;
    }

    /**
     * Checks the balance of an account. If user is not authenticated throws
     * ConnectionFailedException. If Account Not owned throws ConnectionFailedException.
     *
     * @return balance of account with account id
     */
    public BigDecimal checkBalance(int accountId, Context context) throws SQLException, ConnectionFailedException {
        DatabaseHelper dbh = new DatabaseHelper(context);
        currentUser = dbh.getUserDetails(currentUser.getId());
        // Check to see if customer is authenticated.
        if (!authenticated) {
            throw new ConnectionFailedException();
        }
        // Check to see if the customer owns the account
        // accountOwned(currentUser, accountId)
        if (!accountOwned((Customer) currentUser, accountId)) {
            throw new ConnectionFailedException();
        }
        // Get the balance from the database and return it
        BigDecimal balance = dbh.getBalance(accountId);
        return balance;
    }

    /**
     * Make a withdrawal to an account, if not enough funds InsuffiecintFundsException thrown, if
     * withdrawal is not a valid amount IllegalAmountException thrown. If user is not authenticated
     * throws ConnectionFailedException. If Account Not owned throws AccountNotOwnedException.
     *
     * @param withdrawal amount be withdrawn.
     * @return If the funds were withdrawn or not.
     */
    public boolean makeWithdrawal(BigDecimal withdrawal, int accountId, Context context) throws IllegalAmountException,
            SQLException, ConnectionFailedException, InsuffiecintFundsException {
        DatabaseHelper dbh = new DatabaseHelper(context);
        currentUser = dbh.getUserDetails(currentUser.getId());
        // Check to see if customer is authenticated.
        if (!authenticated) {
            throw new ConnectionFailedException();
        }
        // Check to see if the customer owns the account
        if (!accountOwned((Customer) currentUser, accountId)) {
            return false;
        }
        // Check to make sure that deposit is a positive amount.
        if (withdrawal.longValue() < 0) {
            throw new IllegalAmountException();
        }
        // Determine what new balance is
        BigDecimal balance = dbh.getBalance(accountId).subtract(withdrawal);
        // Make sure the account balance is above 0 before making a withdrawal
        if (balance.longValue() < 0) {
            throw new InsuffiecintFundsException();
        }
        // Update the account balance in the database
        boolean success = dbh.updateAccountBalance(balance, accountId);
        // Return whether the deposit was successful
        return success;
    }

    /**
     * Checks to see if the an account is owned by a customer
     *
     * @return if the account is owned by customer or not
     */
    protected boolean accountOwned(Customer customer, int accountId) throws SQLException {
        // Check to see if the account is owned by the user
        boolean accountOwned = false;
        for (Account account : customer.getAccounts()) {
            // If the account id in list is the same as account id
            if (account.getId() == accountId) {
                // set the account owned to true and break the loop
                accountOwned = true;
                break;
            }
        }
        // return if the account is owned by customer.
        return accountOwned;
    }
}
