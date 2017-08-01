package com.bank.bankingapp.activities.terminal.admin;

import android.Manifest;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.activities.login.LoginActivity;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminAllBalanceFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminBalanceFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminCreateUserFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminMessagesFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminPromoteFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.SendMessageFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminViewUserFragment;
import com.bank.bankingapp.activities.terminal.teller.TellerActivity;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseDriverA;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.user.Admin;
import com.bank.bankingapp.user.Customer;
import com.bank.bankingapp.user.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminActivity extends TellerActivity {

    AdminCreateUserFragment createUserFragment;
    AdminPromoteFragment promoteFragment;
    private final int MY_PERMISSION_WRITE_EXTERNAL_STORAGE = 0;

    boolean typeValid;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Intent intent = getIntent();
        atm = new AdminTerminal(this);
        atm.setCurrentUser((Admin) intent.getSerializableExtra("user"));

        // actionBar
        getSupportActionBar().setTitle("Admin Terminal");

    }

    //----------------Create User----------------------------
    public void displayCreateUser(View view) {
        AdminCreateUserFragment adminCreateUserFragment = new AdminCreateUserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, adminCreateUserFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void createUser(View view) {
        AdminTerminal at = new AdminTerminal(this);

        EditText username = (EditText) findViewById(R.id.admin_create_name);
        boolean usernameValid = !username.getText().toString().equals("");
        if (!usernameValid) {
            username.setError("Please Enter A Name");
        }

        EditText age = (EditText) findViewById(R.id.admin_create_age);
        boolean ageValid = !age.getText().toString().equals("");
        if (!ageValid) {
            age.setError("Please Enter An Age");
        }

        EditText address = (EditText) findViewById(R.id.admin_create_address);
        boolean addressValid = !address.getText().toString().equals("");
        if (!addressValid) {
            address.setError("Please Enter An Address");
        }

        EditText password = (EditText) findViewById(R.id.admin_create_password);
        boolean password_valid = !password.getText().toString().equals("");
        if (!password_valid) {
            password.setError("Please Enter A Password");
        }

        if (!usernameValid || !ageValid || !addressValid || !typeValid || !password_valid) {
            return;
        }

        int userId = at.createUser(createUserFragment.getUsername(), createUserFragment.getAge(),
                createUserFragment.getAddress(), createUserFragment.getType(),
                createUserFragment.getPassword());

        //ID Notification dialog box
        final AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.dialog_create_title);
        idNotification.setMessage("Your User ID is " + userId);

        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()

        {
            public void onClick(DialogInterface dialog, int id) {
                createUserFragment.resetFields();
            }
        });

        idNotification.create();
        idNotification.show();
    }

    //------------- Display Messages ------------------
    public void displayMessages(View view) {
        AdminMessagesFragment messagesFragment = new AdminMessagesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, messagesFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Display Users --------------------------
    public void displayViewUser(View view) {
        AdminViewUserFragment viewUserFragment = new AdminViewUserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DatabaseHelper db = new DatabaseHelper(this);
        atm.setCurrentUser(db.getUserDetails(atm.getCurrentUser().getId()));

        transaction.replace(R.id.atm_fragment_container, viewUserFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Display Total Balance of All Accounts --------------------------
    public void displayTotalBalance(View view) {
        AdminAllBalanceFragment balanceFragment = new AdminAllBalanceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DatabaseHelper db = new DatabaseHelper(this);
        atm.setCurrentUser(db.getUserDetails(atm.getCurrentUser().getId()));
        transaction.replace(R.id.atm_fragment_container, balanceFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Display Total Balance of Single User-------------------------
    public void displayUserBalance(View view) {
        final AdminBalanceFragment balanceFragment = new AdminBalanceFragment();
        balanceFragment.setContext(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DatabaseHelper db = new DatabaseHelper(this);
        atm.setCurrentUser(db.getUserDetails(atm.getCurrentUser().getId()));
        transaction.replace(R.id.atm_fragment_container, balanceFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Promote a Teller to Admin -------------------------
    public void displayPromote(View view) {
        promoteFragment = new AdminPromoteFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, promoteFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void promote(View view) {
        promoteFragment.promote(this, (Admin) getIntent().getSerializableExtra("user"));
        displayPromote(view);
    }

    //----------------Serialize----------------------------
    public void confirmSerialize(View view) {
        //Serialize Confirmation dialog box
        AlertDialog.Builder resetConfirmation = new AlertDialog.Builder(this);
        resetConfirmation.setTitle(R.string.serialize_confirmation_title);
        resetConfirmation.setMessage(R.string.serialize_confirmation_message);

        resetConfirmation.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                serialize();
            }
        });

        resetConfirmation.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        resetConfirmation.create();
        resetConfirmation.show();
    }

    public void serialize() {
        try {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_WRITE_EXTERNAL_STORAGE);
            }

            if (isExternalStorageWritable() && isExternalStorageReadable()) {
                File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                File file = new File(root, "database.ser");

                FileOutputStream fileOut = new FileOutputStream(file);

                ObjectOutputStream out = new ObjectOutputStream(fileOut);

                DatabaseHelper db = new DatabaseHelper(this);

                // get all users
                List<User> users = db.getUsers();

                HashMap<Customer, List<Account>> userAccounts = new HashMap<>();

                for (User currUser : users) {
                    if (currUser.getRoleId() == Bank.rolesMap.get(Roles.CUSTOMER).getId()) {
                        Customer currCustomer = (Customer) currUser;
                        userAccounts.put(currCustomer, currCustomer.getAccounts());
                    }
                }

                // get all accounts
                List<Account> accounts = new ArrayList<>();

                for (User user : users) {
                    if (user.getRoleId() == Bank.rolesMap.get(Roles.CUSTOMER).getId()) {
                        for (Integer accountId : db.getAccountIds(user.getId())) {
                            accounts.add(db.getAccountDetails(accountId));
                        }
                    }
                }

                //get all passwords
                List<String> passwords = new ArrayList<>();

                for (User user : users) {
                    passwords.add(db.getPassword(user.getId()));
                }

                out.writeObject(users);
                out.writeObject(accounts);
                out.writeObject(passwords);
                out.writeObject(userAccounts);

                out.close();
                fileOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create .ser file", Toast.LENGTH_SHORT).show();
        }
    }

    /* Checks if external storage is available for read and write */

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    //----------- Log out Admin Terminal-------------------------
    public void logOut(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
