package com.bank.bankingapp.terminals;

import android.content.Context;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.PasswordHelpers;
import com.bank.bankingapp.generics.AccountTypes;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.user.Teller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class TellerTerminal extends ATM implements Serializable {

    private static final long serialVersionUID = -3197891106717343883L;
    private boolean currentTellerAuthenticated;
    private Teller currentTeller;

    public TellerTerminal(Context context) {
        super(context);
        designatedUserId = Bank.rolesMap.get(Roles.CUSTOMER).getId();
    }

    /**
     * Makes a new account and adds it to the database.
     *
     * @param name    of the account
     * @param balance of the account
     * @param type    of the account
     */
    public int makeNewAccount(String name, BigDecimal balance, long type) {
        // Insert the account into the database

        // Test for balance owing accounts
        boolean isNeg = balance.compareTo(new BigDecimal(0)) < 0;
        if (type != Bank.accountsMap.get(AccountTypes.BOA).getId() && isNeg) {
            return -1;
        }
        if (type == Bank.accountsMap.get(AccountTypes.BOA).getId() && !isNeg) {
            return -1;
        }
        // Insert account
        int accountId = (int) db.insertAccount(name, balance, type);
        if (accountId == -1) {
            return -1;
        }
        // Insert user - account relation
        int result = (int) db.insertUserAccount(this.currentUser.getId(), accountId);
        if (result != -1) {
            // If account is Chequing, switch to savings if balance < 1000
            boolean isSavings =
                    db.getAccountType(accountId) == Bank.accountsMap
                            .get(AccountTypes.SAVING)
                            .getId();
            if (db.getBalance(accountId).compareTo(new BigDecimal(1000)) < 0
                    && isSavings) {
                db.updateAccountType(Bank.accountsMap.get(AccountTypes.CHEQUING)
                        .getId(), accountId);
                db.insertMessage(currentUser.getId(),
                        "Your Savings account with account id: " + accountId
                                + " has been transitioned into a Chequing account because the accounts balance was less then 1000$.");
            }
            return accountId;
        }
        return -1;
    }

    /**
     * Authenticates the current teller on teller machine.
     *
     * @param password of customer
     * @return if the customer was authenticated or not
     */
    public boolean loginTeller(int tellerId, String password) {
        currentTellerAuthenticated =
                (PasswordHelpers.comparePassword(db.getPassword(tellerId), password)
                        && db.getUserRole(tellerId) == Bank.rolesMap.get(Roles.TELLER)
                        .getId());
        if (currentTellerAuthenticated) {
            currentTeller = (Teller) db.getUserDetails(tellerId);
        }
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
    public int makeNewUser(String name, int age, String address, String password) {
        // Check to see that the teller is authenticated
        // Get the roleId of the CUSTOMER
        int roleId = 0;
        for (int r : Bank.rolesMap.getIds()) {
            if (db.getRole(r).contentEquals("CUSTOMER")) {
                roleId = r;
                break;
            }
        }
        // Insert the new user into database
        return (int) db.insertNewUser(name, age, address, roleId, password);
    }

    /**
     * Gives interest to a given account if the customer, teller are authenticated and the customer
     * owns the account with given account id.
     *
     * @param accountId id of the account
     */
    public void giveInterest(int accountId) {
        // Determine the account balance
        BigDecimal balance = db.getBalance(accountId);
        // Determine the account interest rate
        BigDecimal interestRate =
                db.getInterestRate(db.getAccountType(accountId));
        // Determine the new balance after interest is added.
        balance = balance.add(balance.multiply(interestRate));
        // Update the account balance
        if (db.updateAccountBalance(balance, accountId)) {
            String message =
                    "Interest has been added to your account: " + db
                            .getAccountDetails(accountId)
                            .getName() + " which has an account id: " + accountId;
            db.insertMessage(currentUser.getId(), message);
        }
        setCurrentUser(db.getUserDetails(getCurrentUser().getId()));
    }

    /**
     * Gives interest to all accounts of the customer if the customer, teller are authenticated.
     */
    public void giveInterestAll() {
        // loop through accounts owned by customer, given interest.
        for (int accountId : db.getAccountIds(currentUser.getId())) {
            giveInterest(accountId);
        }
        setCurrentUser(db.getUserDetails(getCurrentUser().getId()));
    }

    /**
     */
    public boolean updateUserInformation(String name, String address, String password, int age) {
        password = PasswordHelpers.passwordHash(password);
        boolean updatedname = db.updateUserName(name, currentUser.getId());
        boolean updatedaddress = db.updateUserAddress(address, currentUser.getId());
        boolean updatedpassword = db
                .updateUserPassword(password, currentUser.getId());
        boolean updatedage = db.updateUserAge(age, currentUser.getId());
        return updatedname && updatedaddress && updatedpassword && updatedage;
    }

    public void updateName(String name) {
        db.updateUserName(name, currentUser.getId());
    }

    public void updateAddress(String address) {
        db.updateUserAddress(address, currentUser.getId());
    }

    public void updateAge(int age) {
        db.updateUserAge(age, currentUser.getId());
    }

    public void updatePassword(String password) {
        PasswordHelpers.passwordHash(password);
        db.updateUserPassword(password, currentUser.getId());
    }

    /**
     * deAuthenticates the currentUser.
     */
    public void logOutCustomer() {
        authenticated = false;
    }

    public ArrayList<Message> getTellerMessages() {
        ArrayList<Message> messages;
        messages = db.getAllMessages(currentTeller.getId());
        return messages;
    }

    /**
     * Leaves a message for a customer with customer id.
     *
     * @param customerId id of the customer
     * @param message    message that is being left, must be less the 512 characters.
     * @return
     */
    public boolean leaveMessage(int customerId, String message) {
        if (db.getUserDetails(customerId) == null) {
            System.out.println("User with user id could not be found in the database.");
            return false;
        }
        if (!(db.getUserDetails(customerId).getRoleId() == Bank.rolesMap.get(Roles.CUSTOMER).getId())) {
            System.out.println("User is not a customer!");
            return false;
        }
        if (db.insertMessage(customerId, message) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
