package com.bank.bankingapp.bank;

import android.content.Context;

import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.DatabaseInsertException;
import com.bank.bankingapp.exceptions.IllegalAmountException;
import com.bank.bankingapp.exceptions.InsuffiecintFundsException;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.terminals.ATM;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.terminals.Terminal;
import com.bank.bankingapp.user.Admin;
import com.bank.bankingapp.user.Customer;
import com.bank.bankingapp.user.Teller;
import com.bank.bankingapp.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    DatabaseHelper db;
    private Terminal terminal;

    public UserInterface(Terminal terminal, Context context) {
        this.terminal = terminal;
        db = new DatabaseHelper(context);
    }

    /**
     * This function continually prompts the user for a integer until a valid integer is given.
     * Returns integer.
     *
     * @param br Buffered Reader
     * @return an integer
     */
    private static int getValidInt(BufferedReader br) {
        int userInput = 0;
        do {
            try {
                userInput = Integer.parseInt(br.readLine());
                return userInput;
            } catch (NumberFormatException e) {
                System.out.println("Your input cannot be recognized as a valid integer. Please try again.");
            } catch (IOException e) {
                System.out.println("IO Exception, Please try again.");
            }
        } while (true);
    }

    /**
     * This function continually prompts the user for a BigDecimal until a valid BigDecimal is given.
     * Returns BigDecimal.
     *
     * @param br Buffered Reader
     * @return a BigDecimal
     */
    private static BigDecimal getValidBigDecimal(BufferedReader br) throws IOException {
        BigDecimal userInput;
        do {
            try {
                userInput = new BigDecimal(br.readLine());
                return userInput;
            } catch (NumberFormatException e) {
                System.out.println("Your input cannot be recognized as a BigDecimal. Please try again.");
            }
        } while (true);
    }

    /**
     * Display's the correct interface for the terminal this object was initialized with.
     */
    public void displayInterface() throws IOException, SQLException, DatabaseInsertException {
        if (terminal instanceof TellerTerminal) {
            TellerInterface();
        } else if (terminal instanceof ATM) {
            ATMInterface();
        } else if (terminal instanceof AdminTerminal) {
            AdminInterface();
        }
    }

    /**
     * Displays the interface for the ATM terminal.
     */
    private void ATMInterface() throws IOException, SQLException, DatabaseInsertException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nYou are in the ATM interface.");
        ATM atm = (ATM) terminal;
        // Log in to ATM
        loginInterface(atm);
        // set to true when you we want to stop looping
        boolean exit = false;
        do {
            // Display Interface
            System.out.println("\nPlease enter one of the following numbers:");
            System.out.println("1. List Accounts and Balances.");
            System.out.println("2. Make Deposit");
            System.out.println("3. Check balance");
            System.out.println("4. Make withdrawal");
            System.out.println("5. View my messages");
            System.out.println("6. Exit");
            int choiceInt = getValidInt(br);
            switch (choiceInt) {
                case 1:
                    List<Account> accounts = atm.listAccounts();
                    System.out.println("ACCOUNTS: <name>   <balance>   <type>");
                    if (accounts.size() != 0) {
                        for (Account account : accounts) {
                            System.out
                                    .println("  - " + account.getName() + "   " + account.getBalance().toString()
                                            + "   " + db.getAccountTypeName(account.getType()));
                        }
                    } else {
                        System.out.println("  EMPTY");
                    }
                    break;
                case 2:
                    // Deposit amount into account with id
                    System.out.println("\nPlease enter the amount you wish to deposit and account id.");
                    System.out.print("AMOUNT: ");
                    BigDecimal depositAmount = getValidBigDecimal(br);
                    System.out.print("ACCOUNT ID: ");
                    int accountId = getValidInt(br);
                    try {
                        boolean success = atm.makeDeposit(depositAmount, accountId);
                        if (!success) {
                            System.out.println("Amount was not successfully deposited from account.");
                        } else {
                            System.out.println("Amount was successfully deposited into account.");
                        }
                    } catch (IllegalAmountException e) {
                        System.out.println(
                                "Illegal amounts cannot be deposited, Please try again with a positive number");
                    } catch (ConnectionFailedException e) {
                        System.out.println("The account with id" + accountId + " does not belong to user.");
                    }
                    break;
                case 3:
                    // Check the balance of account with account id
                    System.out.println(
                            "\nPlease enter the amount id of the account you wish to check the balance of.");
                    System.out.print("ACCOUNT ID: ");
                    int accountIdCheck = getValidInt(br);
                    try {
                        BigDecimal balance = atm.checkBalance(accountIdCheck);
                        System.out.println("The balance of account is " + balance.toString());
                    } catch (ConnectionFailedException e) {
                        System.out
                                .println("The account with id" + accountIdCheck + " does not belong to user.");
                    }
                    break;
                case 4:
                    // Withdraw amount from account with account id
                    System.out.println("\nPlease enter the amount you wish to withdraw and account id.");
                    System.out.print("AMOUNT: ");
                    BigDecimal withdrawAmount = getValidBigDecimal(br);
                    System.out.print("ACCOUNT ID: ");
                    int accountIdWithdraw = getValidInt(br);
                    try {
                        boolean success = atm.makeWithdrawal(withdrawAmount, accountIdWithdraw);
                        if (!success) {
                            System.out.println("Amount was not successfully withdrawn from account.");
                        } else {
                            System.out.println("Amount was successfully withdrawn from account.");
                        }
                    } catch (IllegalAmountException e) {
                        System.out.println(
                                "Illegal amounts cannot be deposited, Please try again with a positive number");
                    } catch (ConnectionFailedException e) {
                        System.out.println(
                                "The account with id" + accountIdWithdraw + " does not belong to user.");
                    } catch (InsuffiecintFundsException e) {
                        System.out
                                .println("The is not sufficient funds in the account to make that withdrawal");
                    }
                    break;
                case 5:
                    viewMessages(terminal, br);
                    break;
                case 6:
                    // Exit the loop.
                    exit = true;
                    break;
            }

        } while (!exit);

    }

    /**
     * Displays the interface for the Teller Machine
     */
    private void TellerInterface() throws IOException, SQLException, DatabaseInsertException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean success = false;

        System.out.println("\nYou are in Teller Interface.");
        TellerTerminal tellerTerminal = (TellerTerminal) terminal;

        // Log in to ATM
        while (!success) {
            System.out
                    .println(
                            "Please login to the teller terminal as a Teller with your USERID and PASSWORD.");
            System.out.print("USERID: ");
            int tellerId = getValidInt(br);
            System.out.print("PASSWORD: ");
            String password = br.readLine();
            try {
                success = tellerTerminal.loginTeller(tellerId, password);
                if (!success) {
                    System.out.println("Teller log in Failed.\n");
                }
            } catch (Exception e) {
                System.out.println("Teller log in Failed.\n");
            }
        }

        System.out.println("User logged in successfully.");
        // set to true when you we want to stop looping
        boolean exit = false;
        do {
            // Display Interface
            System.out.println("\nPlease enter one of the following numbers:");
            System.out.println("1. Authenticate new User (Required for options 3-9).");
            System.out.println("2. Make new User");
            System.out.println("3. Make new account");
            System.out.println("4. Give interest to a single account");
            System.out.println("5. Give interest to all of users account");
            System.out.println("6. Make a deposit");
            System.out.println("7. Make a withdrawal");
            System.out.println("8. Check balance");
            System.out.println("9. Check total balance of current user's accounts");
            System.out.println("10. Update user Information");
            System.out.println("11. View messages of customer.");
            System.out.println("12. View my own messages.");
            System.out.println("13. Leave a message for a customer.");
            System.out.println("14. close customer session");
            System.out.println("15. Exit");
            int tellerInput = getValidInt(br);
            switch (tellerInput) {
                case 1:
                    // Log in a new user
                    loginInterface(tellerTerminal);
                    break;
                case 2:
                    // Create a new user
                    System.out
                            .println("\nTo create a new user please input a name, age, address, and password:");
                    System.out.print("NAME: ");
                    String name = br.readLine();
                    System.out.print("AGE: ");
                    int age = getValidInt(br);
                    System.out.print("ADDRESS: ");
                    String address = br.readLine();
                    System.out.print("PASSWORD: ");
                    String newCustomerPassword = br.readLine();
                    int newUserId = tellerTerminal.makeNewUser(name, age, address, newCustomerPassword);
                    if (newUserId > 2) {
                        System.out.println("Your new user ID is: " + newUserId + ".");
                    } else {
                        System.out.println("User was not successfully created. Please try again.");
                    }
                    break;
                case 3:
                    // Create a new account for currently authenticated user
                    System.out.println(
                            "\nTo create a new account for current user please input a name, balance, and account type id.");
                    System.out.print("NAME: ");
                    String accountName = br.readLine();
                    System.out.print("Balance: ");
                    BigDecimal balance = getValidBigDecimal(br);
                    System.out.print("TYPE: ");
                    int accountType = getValidInt(br);
                    int newAccountId = tellerTerminal.makeNewAccount(accountName, balance, accountType);
                    if (newAccountId > 0) {
                        System.out.println("Your new account ID is: " + newAccountId + ".");
                    } else {
                        System.out.println("Account was not successfully created. Please try again.");
                    }
                    break;
                case 4:
                    // Give interest to account with account id
                    System.out
                            .println(
                                    "\nPlease enter the account id of the account you are giving interest too.");
                    System.out.print("ACCOUNTID: ");
                    int accountId = getValidInt(br);
                    tellerTerminal.giveInterest(accountId);
                    System.out.println("Interest has been successfully added to account.");
                    break;
                case 5:
                    // Give interest to all accounts owned by user.
                    tellerTerminal.giveInterestAll();
                    System.out.println("\nInterest has been successfully added to all users accounts.");
                    break;
                case 6:
                    // Deposit to account
                    System.out.println("\nPlease enter the amount you are depositing and the account id.");
                    System.out.print("AMOUNT: ");
                    BigDecimal amountDeposit = getValidBigDecimal(br);
                    System.out.print("ACCOUNTID: ");
                    int accountIdDeposit = getValidInt(br);
                    try {
                        tellerTerminal.makeDeposit(amountDeposit, accountIdDeposit);
                        System.out.println("Amount was successfully deposited into account.");
                    } catch (IllegalAmountException e) {
                        System.out.println(
                                "Illegal amounts cannot be deposited, Please try again with a positive number");
                    } catch (ConnectionFailedException e) {
                        System.out.println(
                                "The account with id " + accountIdDeposit + " does not belong to user.");
                    } catch (Exception e) {
                        System.out.println("Deposit failed.");
                    }
                    break;
                case 7:
                    // Withdraw from account
                    System.out.println("\nPlease enter the amount you are withdrawing and the account id.");
                    System.out.print("AMOUNT: ");
                    BigDecimal amountWithdraw = getValidBigDecimal(br);
                    System.out.print("ACCOUNTID: ");
                    int accountIdWithdraw = getValidInt(br);
                    try {
                        tellerTerminal.makeWithdrawal(amountWithdraw, accountIdWithdraw);
                        System.out.println("Amount was successfully withdrawn from account.");
                    } catch (IllegalAmountException e) {
                        System.out.println(
                                "Illegal amounts cannot be deposited, Please try again with a positive number");
                    } catch (ConnectionFailedException e) {
                        System.out.println(
                                "The account with id" + accountIdWithdraw + " does not belong to user.");
                    } catch (InsuffiecintFundsException e) {
                        System.out
                                .println("The is not sufficient funds in the account to make that withdrawal");
                    } catch (Exception e) {
                        System.out.println("Withdrawal Failed.");
                    }
                    break;
                case 8:
                    // Check the balance of account with id
                    System.out.println(
                            "\nPlease enter the account id of the account you wish to check balance of.");
                    System.out.print("ACCOUNTID: ");
                    try {
                        int accountIdCheckBalance = getValidInt(br);
                        BigDecimal accountBalance = tellerTerminal.checkBalance(accountIdCheckBalance);
                        System.out.println("The current account balance is: " + accountBalance.toString());
                    } catch (ConnectionFailedException e) {
                        System.out.println(
                                "Current Customer does not own account with given account id or the user has not been authenticated.");
                    }
                    break;
                case 9:
                    // Show total balance of user's accounts
                    int currUserId;
                    // Make sure user is logged in
                    try {
                        currUserId = tellerTerminal.getCurrentUser().getId();
                    } catch (Exception e) {
                        System.out.println("Not logged in as user.");
                        break;
                    }
                    BigDecimal totalBal = tellerTerminal.addBalances(currUserId);
                    if (totalBal != null) {
                        System.out.println("The total balance of the user is: " + totalBal.toString());
                    } else {
                        System.out.println("Error");
                    }
                    break;
                case 10:
                    // Update User information
                    System.out.println("Please enter the Users new information");
                    System.out.print("NAME: ");
                    String username = br.readLine();
                    System.out.print("ADDRESS: ");
                    String useraddress = br.readLine();
                    System.out.print("PASSWORD: ");
                    String userpassword = br.readLine();
                    System.out.print("AGE: ");
                    int userage = getValidInt(br);
                    boolean updated = tellerTerminal
                            .updateUserInformation(username, useraddress, userpassword, userage);
                    if (updated) {
                        System.out.println("User information successfully updated");
                    } else {
                        System.out.println("User information not updated");
                    }
                    break;
                case 11:
                    viewMessages(terminal, br);
                    break;
                case 12:
                    ArrayList<Message> messages;
                    messages = tellerTerminal.getTellerMessages();
                    if (messages == null) {
                        break;
                    }
                    if (messages.size() < 1) {
                        System.out.println("You currently have no messages!");
                        break;
                    }
                    System.out.println("Displaying all of your messages: ");
                    int message_number = 0;
                    for (Message m : messages) {
                        String message_preview = "";
                        String message_status = "";
                        if (m.getMessage().length() <= 20) {
                            message_preview = m.getMessage();
                        } else {
                            message_preview = m.getMessage().substring(0, 20).trim() + "...";
                        }
                        if (m.isViewed()) {
                            message_status = "READ";
                        } else {
                            message_status = "UNREAD";
                        }
                        System.out.println("~ Message Number: " + message_number);
                        System.out.println("~ Message Status: " + message_status);
                        System.out.println("~ Message Preview: " + message_preview);
                        System.out.println();
                        message_number++;
                    }
                    int n;
                    boolean valid_number;
                    do {
                        System.out.println("Enter the number of the messages you wish to view.");
                        System.out.print("MESSAGE NUMBER: ");
                        n = getValidInt(br);
                        valid_number = n >= 0 && n <= message_number;
                        if (!valid_number) {
                            System.out.println("That is not a valid message number. Please try again.");
                        }
                    } while (!valid_number);
                    //System.out.println(messages.get(n).viewMessage());
                    System.out.println();
                    break;
                case 13:
                    System.out.println(
                            "Enter the customer id of the customer you want to leave a message for and then the message.");
                    System.out.print("CUSTOMER ID: ");
                    int userId = getValidInt(br);
                    System.out.print("MESSAGE: ");
                    String message = br.readLine();
                    if (tellerTerminal.leaveMessage(userId, message)) {
                        System.out.println("Message has successfully left for user.");
                    } else {
                        System.out.println("Message failed to send.");
                    }
                    break;
                case 14:
                    // Deauthenticate user
                    System.out.println("\nLogging out current user.");
                    tellerTerminal.logOutCustomer();
                    System.out.println("Current Customer has been logged out.");
                    break;
                case 15:
                    // Exit loop
                    exit = true;
                    break;
            }
        } while (!exit);

    }

    /**
     * Displays the interface for the admin terminal to the user.
     */
    private void AdminInterface() throws IOException, SQLException, DatabaseInsertException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Notify user they are in admin mode.
        System.out.println("\nYou are in Admin mode.");
        AdminTerminal adminTerminal = (AdminTerminal) terminal;
        loginInterface(adminTerminal);
        int userId;
        boolean exit = false;
        do {
            // Display Interface
            System.out.println("\nPlease enter one of the following numbers:");
            System.out.println("1. Create New Admin");
            System.out.println("2. Create New Teller");
            System.out.println("3. View all Admins");
            System.out.println("4. View all Tellers");
            System.out.println("5. View all Customers");
            System.out.println("6. View total balance of all of a users accounts");
            System.out.println("7. View total balance of all accounts");
            System.out.println("8. Leave a message for a user.");
            System.out.println("9. View my messages.");
            System.out
                    .println("10. View specific message in database without changing its status to viewed.");
            System.out.println("11. Promote a Teller to an Admin.");
            System.out.println("12. Exit");
            int choiceInt = getValidInt(br);
            switch (choiceInt) {
                case 1:
                    // Create new admin
                    // Find out the accountTypeId for Admins.
                    int adminTypeId = Bank.rolesMap.get(Roles.ADMIN).getId();
                    System.out.println("\nPlease enter information to create an Admin.");
                    System.out.print("USERNAME: ");
                    String userName = br.readLine();
                    // Get information we need to create an admin account.
                    System.out.print("AGE: ");
                    int age = getValidInt(br);
                    System.out.print("ADDRESS: ");
                    String address = br.readLine();
                    System.out.print("PASSWORD: ");
                    String password = br.readLine();
                    // Create Admin account
                    System.out.print("Now creating an Admin... ");
                    int newId = adminTerminal.createUser(userName, age, address, adminTypeId, password);
                    if (newId > 0) {
                        System.out.println("Admin created. The new user Id for your Admin is: " + newId);
                    } else {
                        System.out.println("Admin was not successfully created. Please try again.");
                    }
                    break;

                case 2:
                    // Create new teller
                    // Find out the accountTypeId for Tellers.
                    int tellerTypeId = Bank.rolesMap.get(Roles.TELLER).getId();
                    // Allow admin to create teller accounts.
                    System.out.println("\nPlease enter information to create a teller.");
                    System.out.print("USERNAME: ");
                    userName = br.readLine();
                    // Get information we need to create a teller account.
                    System.out.print("AGE: ");
                    age = getValidInt(br);
                    System.out.print("ADDRESS: ");
                    address = br.readLine();
                    System.out.print("PASSWORD: ");
                    password = br.readLine();
                    // Create teller account
                    System.out.print("Now creating a Teller... ");
                    newId =
                            (int) db.insertNewUser(userName, age, address, tellerTypeId, password);
                    if (newId > 0) {
                        System.out.println("Teller created. The new user Id for the Teller is: " + newId);
                    } else {
                        System.out.println("Teller was not successfully created. Please try again.");
                    }
                    break;

                case 3:
                    // View all admins
                    List<Admin> admins = new ArrayList<Admin>();
                    for (User user : adminTerminal.getUserType(Bank.rolesMap.get(Roles.ADMIN).getId())) {
                        admins.add((Admin) user);
                    }
                    System.out.println("\nAll current Admins:");
                    if ((admins.size() != 0)) {
                        for (Admin admin : admins) {
                            System.out.println("  - " + admin.getName());
                        }
                    } else {
                        System.out.println("  EMPTY");
                    }
                    break;

                case 4:
                    // View all tellers
                    List<Teller> tellers = new ArrayList<Teller>();
                    for (User user : adminTerminal.getUserType(Bank.rolesMap.get(Roles.TELLER).getId())) {
                        tellers.add((Teller) user);
                    }
                    System.out.println("\nAll current Tellers:");
                    if ((tellers.size() != 0)) {
                        for (Teller teller : tellers) {
                            System.out.println("  - " + teller.getName());
                        }
                    } else {
                        System.out.println("  EMPTY");
                    }
                    break;

                case 5:
                    // View all admins
                    List<Customer> customers = new ArrayList<Customer>();
                    for (User user : adminTerminal.getUserType(Bank.rolesMap.get(Roles.CUSTOMER).getId())) {
                        customers.add((Customer) user);
                    }
                    System.out.println("\nAll current Customers:");
                    if ((customers.size() != 0)) {
                        for (Customer customer : customers) {
                            System.out.println("  - " + customer.getName());
                        }
                    } else {
                        System.out.println("  EMPTY");
                    }
                    break;

                case 6:
                    // Show total balance of user's accounts
                    System.out.println(
                            "Please enter the userId of the customer to calculate the total balance of.");
                    System.out.println("USERID: ");
                    userId = getValidInt(br);
                    try {
                        BigDecimal totalBal = adminTerminal.addBalances(userId);
                        System.out.println("The total balance of the user is: " + totalBal.toString());
                    } catch (Exception e) {
                        System.out.println("That is not the user ID of a customer!");
                    }
                    break;

                case 7:
                    // Show total balance of all accounts
                    System.out.println(
                            "The total balance of all accounts is: " + adminTerminal.addAllBalances().toString());
                    break;
                case 8:
                    System.out.println(
                            "Please enter the user id of the user you wish to leave a message for. Message must less then 512 charaters");
                    System.out.print("USERID: ");
                    userId = getValidInt(br);
                    System.out.print("MESSAGE: ");
                    String message = br.readLine();
                    if (adminTerminal.leaveMessage(userId, message) == true) {
                        System.out.println("Message sent successfully");
                    } else {
                        System.out.println("Message was not sent.");
                    }
                    break;
                case 9:
                    viewMessages(terminal, br);
                    break;
                case 10:
                    System.out.println(
                            "Enter the message id of the message you wish to view. Message status will not be changed.");
                    int messageId = getValidInt(br);
                    message = adminTerminal.viewMessageUnseen(messageId);
                    System.out.println("Message: " + message);
                    break;
                case 11:
                    System.out.println("Please enter the user id of the teller you wish to promote.");
                    System.out.print("USER ID: ");
                    userId = getValidInt(br);
                    if (adminTerminal.promoteTeller(userId) == true) {
                        System.out.println("Teller successfully promoted.");
                    } else {
                        System.out.println("Teller promotion failed.");
                    }
                    break;
                case 12:
                    // Exit the loop.
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    /**
     * Displays the ui and logs in the user into the correct machine.
     */
    private void loginInterface(Terminal terminal) throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nPlease login to your account using your user id and password.");
        boolean success = false;
        do {
            // get login credentials from teller
            System.out.print("USERID: ");
            int userId = getValidInt(br);
            System.out.print("PASSWORD: ");
            String password = br.readLine();

            try {
                success = terminal.logIn(userId, password);
                if (!success) {
                    System.out.println("Login failed, Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("Login failed, Please try again.\n");
            }


            if (terminal instanceof ATM && success) {
                System.out.println("Logged in successfully.");
                Customer curr_user = (Customer) db.getUserDetails(userId);
                System.out.println("\nNAME: " + curr_user.getName());
                System.out.println("ADDRESS: " + curr_user.getAddress());
                System.out.println("ACCOUNTS: <name>   <balance>   <type>");
                List<Account> accounts = ((ATM) terminal).listAccounts();
                if (accounts.size() != 0) {
                    for (Account account : accounts) {
                        System.out.println("  - " + account.getName() + "   " + account.getBalance().toString()
                                + "   " + db.getAccountTypeName(account.getType()));
                    }
                } else {
                    System.out.println("  EMPTY");
                }
            }
        } while (!success);
    }

    private boolean viewMessages(Terminal terminal, BufferedReader br) {
        ArrayList<Message> messages;

        messages = terminal.viewAllMessages();
        if (messages == null) {
            System.out.println("Current user is not authenticated");
            return false;
        }
        if (messages.size() < 1) {
            System.out.println("You currently have no messages!");
            return true;
        }
        System.out.println("Displaying all of your messages: ");
        int message_number = 0;
        for (Message m : messages) {
            String message_preview = "";
            String message_status = "";
            if (m.getMessage().length() <= 20) {
                message_preview = m.getMessage();
            } else {
                message_preview = m.getMessage().substring(0, 20).trim() + "...";
            }
            if (m.isViewed()) {
                message_status = "READ";
            } else {
                message_status = "UNREAD";
            }
            System.out.println("~ Message Number: " + message_number);
            System.out.println("~ Message Status: " + message_status);
            System.out.println("~ Message Preview: " + message_preview);
            System.out.println();
            message_number++;
        }
        int n;
        boolean valid_number;
        do {
            System.out.println("Enter the number of the messages you wish to view.");
            System.out.print("MESSAGE NUMBER: ");
            n = getValidInt(br);
            valid_number = n >= 0 && n < message_number;
            if (!valid_number) {
                System.out.println("That is not a valid message number. Please try again.");
            }
        } while (!valid_number);
        //System.out.println(messages.get(n).viewMessage());
        System.out.println();
        return true;
    }
}
