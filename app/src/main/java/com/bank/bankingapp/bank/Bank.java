package com.bank.bankingapp.bank;

import android.content.Context;

import com.bank.bankingapp.database.DatabaseDriverA;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.DatabaseInsertException;
import com.bank.bankingapp.maps.AccountsMap;
import com.bank.bankingapp.maps.RolesMap;
import com.bank.bankingapp.terminals.ATM;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.terminals.TellerTerminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;


public class Bank {
    public static AccountsMap accountsMap;
    public static RolesMap rolesMap;

    /**
     * creates a map for the database and initializes the ROLES and ACCOUNT Types
     * inside the database
     * @param context
     */
    public static void createMaps(Context context) {

        DatabaseHelper db = new DatabaseHelper(context);

        // Add User Roles to database.
        db.insertRole("ADMIN");
        db.insertRole("TELLER");
        db.insertRole("CUSTOMER");

        // Add Account Types to database.
        db.insertAccountType("CHEQUING", new BigDecimal("0.1"));
        db.insertAccountType("SAVING", new BigDecimal("0.2"));
        db.insertAccountType("TFSA", new BigDecimal("0.3"));
        db.insertAccountType("RSA", new BigDecimal("0.4"));
        db.insertAccountType("BOA", new BigDecimal("0.2"));

        accountsMap = new AccountsMap(context);
        rolesMap = new RolesMap(context);
    }
}

