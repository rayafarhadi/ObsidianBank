package com.bank.bankingapp.database;

import android.content.Context;

import com.bank.bank.Bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DatabaseUpdateHelper {

    DatabaseDriverA db;

    public DatabaseUpdateHelper(Context context) {
        db = new DatabaseDriverA(context);
    }

    /**
     * Update the role name of a given role in the role table.
     *
     * @param name the new name of the role.
     * @param id   the current ID of the role.
     * @return true if successful, false otherwise.
     */
    public boolean updateRoleName(String name, int id) {

        // Validate Input
        boolean valid = (name != null && !name.equals("")) && DatabaseValueCheckerHelper.roleIdChecker(id);

        boolean complete;

        // If input is valid then update the role name and return whether it was successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateRoleName(name, id);
        } else {
            complete = false;
        }
        Bank.rolesMap.update();
        return complete;
    }


    /**
     * Update the user's name.
     *
     * @param name the new name
     * @param id   the current id
     * @return true if it works, false otherwise.
     */
    public boolean updateUserName(String name, int id) {

        // Validate input
        boolean valid = name != null && !name.equals("");

        boolean complete;

        // If input is valid update the users name and return whether it was successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateUserName(name, id);
        } else {
            complete = false;
        }
        return complete;
    }


    /**
     * Use this to update the user's age.
     *
     * @param age the new age.
     * @param id  the current id
     * @return true if it succeeds, false otherwise.
     */
    public boolean updateUserAge(int age, int id) {

        // Validate input
        boolean valid = age > 0;

        boolean complete;

        // If input is valid update the users age and return whether it was successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateUserAge(age, id);
        } else {
            complete = false;
        }
        return complete;
    }

    /**
     * update the role of the user.
     *
     * @param roleId the new role.
     * @param id     the current id.
     * @return true if successful, false otherwise.
     */
    public boolean updateUserRole(int roleId, int id) {

        // Validate input
        boolean valid = DatabaseValueCheckerHelper.roleIdChecker(roleId);

        boolean complete;

        // If the input is valid then update the users role and return whether it was successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateUserRole(roleId, id);
        } else {
            complete = false;
        }
        return complete;
    }


    /**
     * Use this to update user's address.
     *
     * @param address the new address.
     * @param id      the current id.
     * @return true if successful, false otherwise.
     */
    public boolean updateUserAddress(String address, int id) {

        // Validate input
        boolean valid = address != null && !address.equals("")
                && DatabaseValueCheckerHelper.addressLengthChecker(address);

        boolean complete;

        // If input is valid then update the users address and return whether it was successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateUserAddress(address, id);
        } else {
            complete = false;
        }
        return complete;
    }


    /**
     * update the name of the account.
     *
     * @param name the new name for the account.
     * @param id   the id of the account to be changed.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountName(String name, int id) {

        // Validate input
        boolean valid = name != null && !name.equals("");

        boolean complete;

        // If input is valid then update the accounts name and return whether it was successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateAccountName(name, id);
        } else {
            complete = false;
        }
        return complete;
    }

    /**
     * update the account balance.
     *
     * @param balance the new balance for the account.
     * @param id      the id of the account.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountBalance(BigDecimal balance, int id) {

        // round balance to 2 decimal places
        balance = DatabaseValueCheckerHelper.balanceRounding(balance);

        return db.updateAccountBalance(balance, id);
    }


    /**
     * update the type of the account.
     *
     * @param typeId the new type for the account.
     * @param id     the id of the account to be updated.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountType(int typeId, int id) {

        // Validate input
        boolean valid = DatabaseValueCheckerHelper.accountTypeIdChecker(typeId);

        boolean complete;

        // If the input is valid then update the account type and return whether it was successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateAccountType(typeId, id);
        } else {
            complete = false;
        }
        return complete;
    }


    /**
     * update the name of an accountType.
     *
     * @param name the new name to be given.
     * @param id   the id of the accountType.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountTypeName(String name, int id) {

        // Validate input
        boolean valid =
                (name != null && !name.equals("")) && DatabaseValueCheckerHelper.accountTypeIdChecker(id);

        boolean complete;

        // If input is valid then update the account types name and return whether it was successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateAccountTypeName(name, id);
        } else {
            complete = false;
        }
        Bank.accountsMap.update();
        return complete;
    }


    /**
     * update the interest rate for this account type.
     *
     * @param interestRate the interest rate to be updated to.
     * @param id           the id of the accountType.
     * @return true if successful, false otherwise.
     */
    public boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) {

        // Validate input
        boolean valid = DatabaseValueCheckerHelper.interestRateChecker(interestRate);

        boolean complete;

        // If input is valid then update the account types interest rate and return whether it was
        // successful.
        // Otherwise return false
        if (valid) {
            complete = db.updateAccountTypeInterestRate(
                    interestRate.setScale(2, RoundingMode.HALF_UP), id);
        } else {
            complete = false;
        }
        Bank.accountsMap.update();
        return complete;
    }

    /**
     * Update the hashed password for user in database
     *
     * @param password The hashed password
     * @param userId   The users id
     * @return true if successful, false otherwise
     */
    public boolean updateUserPassword(String password, int userId){
        return db.updateUserPassword(password, userId);
    }

    /**
     * Updates the a message with message id to viewed in the database.
     *
     * @param messageId of message
     * @return of the update was successful or not.
     */
    public boolean updateUserMessageState(int messageId) {
        try {
            db.updateUserMessageState(messageId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
