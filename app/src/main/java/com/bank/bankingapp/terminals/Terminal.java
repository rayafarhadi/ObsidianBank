package com.bank.bankingapp.terminals;

import java.sql.SQLException;

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
    public boolean logIn(int userId, String password) throws SQLException {
        currentUser = DatabaseSelectHelper.getUserDetails(userId);
        authenticated = (PasswordHelpers
                .comparePassword(DatabaseSelectHelper.getPassword(userId), password)) && (
                currentUser.getRoleId() == designatedUserId);
        return this.authenticated;
    }
}
