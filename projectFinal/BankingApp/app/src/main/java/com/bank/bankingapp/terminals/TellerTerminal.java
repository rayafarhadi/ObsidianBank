package com.bank.bankingapp.terminals;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseInsertHelper;
import com.bank.bankingapp.database.DatabaseSelectHelper;
import com.bank.bankingapp.database.DatabaseUpdateHelper;
import com.bank.bankingapp.database.PasswordHelpers;
import com.bank.bankingapp.exceptions.DatabaseInsertException;
import com.bank.bankingapp.generics.Roles;

import java.math.BigDecimal;
import java.sql.SQLException;

public class TellerTerminal extends ATM {

    private boolean currentTellerAuthenticated;

    public TellerTerminal() {
        designatedUserId = Bank.rolesMap.get(Roles.CUSTOMER).getId();
    }

    /**
     * Makes a new account and adds it to the database.
     *
     * @param name    of the account
     * @param balance of the account
     * @param type    of the account
     */
    public int makeNewAccount(String name, BigDecimal balance, int type)
            throws SQLException, DatabaseInsertException {
        // Insert the account into the database
        try {
            int accountId = DatabaseInsertHelper.insertAccount(name, balance, type);
            int result = DatabaseInsertHelper.insertUserAccount(this.currentUser.getId(), accountId);
            if (result != -1) {
                return result;
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Authenticates the current teller on teller machine.
     *
     * @param password of customer
     * @return if the customer was authenticated or not
     */
    public boolean loginTeller(int tellerId, String password) throws SQLException {
        currentTellerAuthenticated =
                (PasswordHelpers.comparePassword(DatabaseSelectHelper.getPassword(tellerId), password)
                        && DatabaseSelectHelper.getUserRole(tellerId) == Bank.rolesMap.get(Roles.TELLER)
                        .getId());
        return this.currentTellerAuthenticated;
    }

    /**
     * Creates a new user if the teller is authenticated.
     *
     * @param name     of customer
     * @param age      of customer
     * @param address  of customer
     * @param password of customer
     */
    public int makeNewUser(String name, int age, String address, String password)
            throws SQLException, DatabaseInsertException {
        // Check to see that the teller is authenticated
        if (!currentTellerAuthenticated) {
            return -1;
        }
        // Get the roleId of the CUSTOMER
        int roleId = 0;
        for (int r : Bank.rolesMap.getIds()) {
            if (DatabaseSelectHelper.getRole(r).contentEquals("CUSTOMER")) {
                roleId = r;
                break;
            }
        }
        // Insert the new user into database
        return DatabaseInsertHelper.insertNewUser(name, age, address, roleId, password);
    }

    /**
     * Gives interest to a given account if the customer, teller are authenticated and the customer
     * owns the account with given account id.
     *
     * @param accountId id of the account
     */
    public void giveInterest(int accountId) throws SQLException {
        // Check to see that the teller and customer is authenticated
        if (!currentTellerAuthenticated) {
            return;
        }
        if (!authenticated) {
            return;
        }
        // Determine the account balance
        BigDecimal balance = DatabaseSelectHelper.getBalance(accountId);
        // Determine the account interest rate
        BigDecimal interestRate =
                DatabaseSelectHelper.getInterestRate(DatabaseSelectHelper.getAccountType(accountId));
        // Determine the new balance after interest is added.
        balance = balance.add(balance.multiply(interestRate));
        // Update the account balance
        DatabaseUpdateHelper.updateAccountBalance(balance, accountId);
    }

    /**
     * Gives interest to all accounts of the customer if the customer, teller are authenticated.
     */
    public void giveInterestAll() throws SQLException {
        // Check to see that the teller and customer is authenticated
        if (!currentTellerAuthenticated) {
            return;
        }
        if (!authenticated) {
            return;
        }
        // loop through accounts owned by customer, given interest.
        for (int accountId : DatabaseSelectHelper.getAccountIds(currentUser.getId())) {
            giveInterest(accountId);
        }
    }

    /**
     * deAuthenticates the currentUser.
     */
    public void logOutCustomer() {
        authenticated = false;
    }

}
