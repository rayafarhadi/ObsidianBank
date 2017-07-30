package com.bank.bankingapp.bank;

import android.content.Context;

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

    private DatabaseHelper db;
    private Context context;

    /**
     * This is the main method to run your entire program! Follow the Candy Cane instructions to
     * finish this off.
     *
     * @throws IOException
     * @throws DatabaseInsertException
     * @throws SQLException
     * @throws ConnectionFailedException
     */
    public void main(int mode, Context context)
            throws ConnectionFailedException, DatabaseInsertException, IOException {

        db = new DatabaseHelper(context);

        if (mode == -1) {
            runAdminMode();
        }
        // If anything else - including nothing
        else {
            runNormally();
        }
    }

    private void runAdminMode() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Initialize database
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

            // Find out the accountTypeId for Administrators.
            int adminTypeId = getRoleId("ADMIN");
            for (int typeId : db.getRoles()) {
                System.out.println(db.getRole(typeId));
                if (db.getRole(typeId).equals("ADMIN")) {
                    adminTypeId = typeId;
                    break;
                }
            }
            // Prompt admin to make an account
            System.out.println("Please enter information to create an administrator account.");
            System.out.print("USERNAME: ");
            String userName = br.readLine();
            System.out.print("AGE: ");
            int age = Integer.parseInt(br.readLine());
            System.out.print("ADDRESS: ");
            String address = br.readLine();
            System.out.print("PASSWORD: ");
            String password = br.readLine();
            // Create account.
            System.out.print("Now creating your administrator account... ");
            int newID = (int) db.insertNewUser(userName, age, address, adminTypeId, password);
            if (newID == -1) {
                System.out.println("Account creation failed. Delete database and try again.");
            } else {
                System.out
                        .println("Account created. The new user Id for the administrator account is: " + newID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }

    private void runNormally() {
        try {
            accountsMap = new AccountsMap(context);
            rolesMap = new RolesMap(context);

            int menu = 0;
            do {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                // Display Interface
                System.out.println("Enter one of the following numbers to access that interface: ");
                System.out.println("1 - ADMIN Interface.");
                System.out.println("2 - TELLER Interface.");
                System.out.println("3 - ATM Interface.");
                System.out.println("0 - Exit.");
                System.out.print("Enter Selection: ");
                menu = getValidInt(br);
                // If the user entered anything else re-prompt the user.
                if (0 <= menu && menu <= 3) {
                    break;
                } else {
                    System.out.println("Your input was not 0 to 3. Please try again:");
                }
            } while (true);
            // If the user entered 1
            if (menu == 1) {
                UserInterface ui = new UserInterface(new AdminTerminal(context), context);
                ui.displayInterface();
            }
            // If the user entered 2
            else if (menu == 2) {
                UserInterface ui = new UserInterface(new TellerTerminal(context), context);
                ui.displayInterface();
            } else if (menu == 3) {
                UserInterface ui = new UserInterface(new ATM(context), context);
                ui.displayInterface();
            } else {
                System.out.println("Exiting program :)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function continually prompts the user for a integer until a valid integer is given.
     * Returns integer.
     *
     * @param br Buffered Reader
     * @return an integer
     * @throws IOException
     */
    private int getValidInt(BufferedReader br) throws IOException {
        int userInput = 0;
        do {
            try {
                userInput = Integer.parseInt(br.readLine());
                return userInput;
            } catch (NumberFormatException e) {
                System.out.println("Your input cannot be recognized as a valid integer. Please try again.");
            }
        } while (true);
    }

    /**
     * Gets the role id for a specific roleName from the database.
     *
     * @param roleName
     * @return role ID.
     * @throws SQLException
     */
    private int getRoleId(String roleName) throws SQLException {
        // Find out the Role ID for roleName.
        for (int roleId : db.getRoles()) {
            if (db.getRole(roleId).equalsIgnoreCase(roleName)) {
                return roleId;
            }
        }
        return -1;
    }
}
