package com.bank.bankingapp.database;

import android.content.Context;

import com.bank.bankingapp.generics.AccountTypes;
import com.bank.bankingapp.generics.Roles;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DatabaseValueCheckerHelper {

    /**
     * Checks if the roleId is one of the valid Id's in the database
     *
     * @param roleId
     * @return true if the role Id is valid in database
     */
    public boolean roleIdChecker(int roleId) {
        return roleId > 0 && roleId <= Roles.values().length;
    }

    /**
     * Verifies whether the inputed role has the same name as one of the roles
     * in the Enum Roles
     *
     * @param role The role to check
     * @return Whether the given name matches a Roles name
     */
    public boolean roleTypeChecker(String role) {
        // All of the roles
        Roles[] roles = Roles.values();
        boolean valid = false;

        // Go through each role and check if the name matches the name given
        for (int i = 0; i < roles.length; i++) {
            if (role.equals(roles[i].toString())) {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * Verifies whether the account Id is a valid Id
     *
     * @param typeId
     * @return true if the account type id is in the database
     */
    public boolean accountTypeIdChecker(int typeId) {
        return typeId > 0 && typeId <= AccountTypes.values().length;
    }

    /**
     * Verifies whether the inputed account type has the same name as one of the
     * roles in the Enum AccountTypes
     *
     * @return Whether the given name matches a AccountTypes name
     */
    public boolean accountTypeChecker(String account) {
        // All of the account types
        AccountTypes[] accountTypes = AccountTypes.values();
        boolean valid = false;

        // Go through each account type and check if the name matches the name
        // given
        for (int i = 0; i < accountTypes.length; i++) {
            if (account.equals(accountTypes[i].toString())) {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * Verifies if the user owns the account
     *
     * @param userId    - current user
     * @param accountId - account that needs to be verified
     * @return true if the user owns the account
     */
    public boolean userHasAccountChecker(int userId, int accountId, Context context) {

        DatabaseHelper db = new DatabaseHelper(context);

        boolean valid = false;
        // Check if input is valid
        List<Integer> IDs = db.getAccountIds(userId);
        for (int i = 0; i < IDs.size(); i++) {
            if (accountId == IDs.get(i)) {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * Rounds the balance of the user to 2 decimal places
     *
     * @param balance of user
     * @return balance of user rounded to 2 decimal places
     */
    public BigDecimal balanceRounding(BigDecimal balance) {
        return balance.setScale(2, RoundingMode.HALF_DOWN);
    }

    /**
     * Verifies if the address is under 100 characters
     *
     * @param address of user
     * @return True is the address is under 100 characters
     */
    public boolean addressLengthChecker(String address) {
        return address.length() > 0 && address.length() < 100;
    }

    /**
     * Verifies if the interest rate is valid( greater than 0 and less than 1
     *
     * @param interestRate of the user
     * @return True if the interest rate is valid
     */
    public boolean interestRateChecker(BigDecimal interestRate) {
        return interestRate.doubleValue() >= 0 && interestRate.doubleValue() < 1.0;
    }

}
