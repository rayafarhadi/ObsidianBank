package com.bank.bankingapp.bank;

import com.bank.bankingapp.database.DatabaseInsertHelper;
import com.bank.bankingapp.database.DatabaseSelectHelper;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.DatabaseInsertException;
import com.bank.bankingapp.maps.AccountsMap;
import com.bank.bankingapp.maps.RolesMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;


public class Bank {
    public static AccountsMap accountsMap;
    public static RolesMap rolesMap;

    /**
     * This is the main method to run your entire program! Follow the Candy Cane instructions to
     * finish this off.
     *
     * @param argv unused.
     * @throws IOException
     * @throws DatabaseInsertException
     * @throws SQLException
     * @throws ConnectionFailedException
     */
    public static void main(String[] argv)
            throws ConnectionFailedException, SQLException, DatabaseInsertException, IOException {
        if (argv.length != 1) {
            runNormally();
        } else if (argv[0].equals("-1")) {

            runAdminMode();
        }
        // If anything else - including nothing
        else {
            runNormally();
        }
    }

    private static void runAdminMode() {
        Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Initialize database
            DatabaseDriverExtender.initialize(connection);
            // Add User Roles to database.
            DatabaseInsertHelper.insertRole("ADMIN");
            DatabaseInsertHelper.insertRole("TELLER");
            DatabaseInsertHelper.insertRole("CUSTOMER");
            // Add Account Types to database.
            DatabaseInsertHelper.insertAccountType("CHEQUING", new BigDecimal("0.1"));
            DatabaseInsertHelper.insertAccountType("SAVING", new BigDecimal("0.2"));
            DatabaseInsertHelper.insertAccountType("TFSA", new BigDecimal("0.3"));
            DatabaseInsertHelper.insertAccountType("RSA", new BigDecimal("0.4"));
            DatabaseInsertHelper.insertAccountType("BOA", new BigDecimal("0.2"));


            accountsMap = new AccountsMap();
            rolesMap = new RolesMap();

            // Find out the accountTypeId for Administrators.
            int adminTypeId = getRoleId("ADMIN");
            for (int typeId : DatabaseSelectHelper.getRoles()) {
                System.out.println(DatabaseSelectHelper.getRole(typeId));
                if (DatabaseSelectHelper.getRole(typeId).equals("ADMIN")) {
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
            int newID = DatabaseInsertHelper.insertNewUser(userName, age, address, adminTypeId, password);
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

    private static void runNormally() {
        try {
            accountsMap = new AccountsMap();
            rolesMap = new RolesMap();

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
                UserInterface ui = new UserInterface(new AdminTerminal());
                ui.displayInterface();
            }
            // If the user entered 2
            else if (menu == 2) {
                UserInterface ui = new UserInterface(new TellerTerminal());
                ui.displayInterface();
            } else if (menu == 3) {
                UserInterface ui = new UserInterface(new ATM());
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
    private static int getValidInt(BufferedReader br) throws IOException {
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
    private static int getRoleId(String roleName) throws SQLException {
        // Find out the Role ID for roleName.
        for (int roleId : DatabaseSelectHelper.getRoles()) {
            if (DatabaseSelectHelper.getRole(roleId).equalsIgnoreCase(roleName)) {
                return roleId;
            }
        }
        return -1;
    }
}
