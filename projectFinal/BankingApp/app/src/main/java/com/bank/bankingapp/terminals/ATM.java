package com.bank.bankingapp.terminals;

import android.content.Context;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.IllegalAmountException;
import com.bank.bankingapp.exceptions.InsuffiecintFundsException;
import com.bank.bankingapp.generics.AccountTypes;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.user.Customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ATM extends Terminal implements Serializable{

    private static final long serialVersionUID = 7013462395707002850L;

    public ATM(Context context) {
        super(context);
        designatedUserId = Bank.rolesMap.get(Roles.CUSTOMER).getId();
    }

    /**
     * Gets a list of accounts owned by currentUser
     *
     * @return list of accounts owned by currentUser
     */
    public List<Account> listAccounts() throws SQLException {
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
    public boolean makeDeposit(BigDecimal deposit, int accountId)
            throws IllegalAmountException, ConnectionFailedException {

        // Check that the account is a RSA, and makes sure a teller is accessing it
        if (db.getAccountType(accountId) == Bank.accountsMap.get(AccountTypes.RSA)
                .getId() && !(this instanceof TellerTerminal)) {
            return false;
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
        BigDecimal balance = db.getBalance(accountId).add(deposit);
        // Update the account balance in the database
        boolean success = db.updateAccountBalance(balance, accountId);
        setCurrentUser(db.getUserDetails(getCurrentUser().getId()));
        // Return whether the deposit was successful
        return success;


    }

    /**
     * Checks the balance of an account. If user is not authenticated throws
     * ConnectionFailedException. If Account Not owned throws
     * ConnectionFailedException.
     *
     * @return balance of account with account id
     */

    public BigDecimal checkBalance(int accountId) throws ConnectionFailedException {
        // Check to see if customer is authenticated.
        currentUser = db.getUserDetails(currentUser.getId());

        // Check to see if the customer owns the account
        // accountOwned(currentUser, accountId)
        if (!accountOwned((Customer) currentUser, accountId)) {
            throw new ConnectionFailedException();
        }
        // Get the balance from the database and return it
        BigDecimal balance = db.getBalance(accountId);
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
    public boolean makeWithdrawal(BigDecimal withdrawal, int accountId) throws IllegalAmountException,
            ConnectionFailedException, InsuffiecintFundsException {
        currentUser = db.getUserDetails(currentUser.getId());

        // Check that the account is a RSA, and makes sure a teller is accessing it
        if (db.getAccountType(accountId) == Bank.accountsMap.get(AccountTypes.RSA)
                .getId() && !(this instanceof TellerTerminal)) {
            return false;
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
        BigDecimal balance = db.getBalance(accountId).subtract(withdrawal);
        // Make sure the account balance is above 0 before making a withdrawal
        if (balance.longValue() < 0) {
            throw new InsuffiecintFundsException();
        }
        // Update the account balance in the database
        boolean success = db.updateAccountBalance(balance, accountId);

        // If account is Chequing, switch to savings if balance < 1000
        boolean isSavings =
                db.getAccountType(accountId) == Bank.accountsMap.get(AccountTypes.SAVING)
                        .getId();
        if (db.getBalance(accountId).compareTo(new BigDecimal(1000)) < 0
                && isSavings) {
            db.updateAccountType(Bank.accountsMap.get(AccountTypes.CHEQUING)
                    .getId(), accountId);
            db.insertMessage(currentUser.getId(),
                    "Your Savings account with account id: " + accountId
                            + " has been transitioned into a Chequing account because the accounts balance was less then 1000$.");
        }

        setCurrentUser(db.getUserDetails(getCurrentUser().getId()));
        // Return whether the deposit was successful
        return success;

    }

    /**
     * Checks to see if the an account is owned by a customer
     *
     * @return if the account is owned by customer or not
     */
    protected boolean accountOwned(Customer customer, int accountId) {
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
