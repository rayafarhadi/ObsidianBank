package com.bank.bankingapp.activities.login.reset;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.activities.login.LoginActivity;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseDriverA;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.user.Customer;
import com.bank.bankingapp.user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class that Resets the database in the app
 */
public class ResetDatabaseActivity extends AppCompatActivity {

    private final int MY_PERMISSION_READ_EXTERNAL_STORAGE = 0;

    /**
     * Initializes the reset activity and sets the layout of the screen on the app
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
    }

    /**
     * Resets the database of the app and adds the first admin to it
     *
     * @param view
     */

    public void reset(View view) {
        // Creates a map connected to the activity
        Bank.createMaps(this);
        //get new admins information
        EditText name = (EditText) findViewById(R.id.reset_admin_name);
        EditText age = (EditText) findViewById(R.id.reset_admin_age);
        EditText address = (EditText) findViewById(R.id.reset_admin_address);
        EditText password = (EditText) findViewById(R.id.reset_admin_password);

        DatabaseDriverA driver = new DatabaseDriverA(this);
        DatabaseHelper db = new DatabaseHelper(this);

        //Set versions to 1 since the database is being reset
        driver.onUpgrade(driver.getReadableDatabase(), 1, 1);

        //Create admin
        db.insertNewUser(name.getText().toString(), Integer.parseInt(age.getText().toString()),
                address.getText().toString(), Bank.rolesMap.get(Roles.ADMIN).getId(),
                password.getText().toString());

        final Intent intent = new Intent(this, LoginActivity.class);


        //ID Notification dialog box
        final AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.id_notify_title);
        idNotification.setMessage(R.string.id_notify_message);

        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(intent);
            }
        });

        //Reset Confirmation dialog box
        AlertDialog.Builder resetConfirmation = new AlertDialog.Builder(this);
        resetConfirmation.setTitle(R.string.reset_confirmation_title);
        resetConfirmation.setMessage(R.string.reset_confirmation_message);

        resetConfirmation.setPositiveButton(R.string.dialog_reset, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                idNotification.create();
                idNotification.show();
            }
        });

        resetConfirmation.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        resetConfirmation.create();
        resetConfirmation.show();
    }

    public void confirmDeserialize(View view) {
        //Reset Confirmation dialog box
        AlertDialog.Builder deserializeConfirmation = new AlertDialog.Builder(this);
        deserializeConfirmation.setTitle(R.string.deserialize_confirmation_title);
        deserializeConfirmation.setMessage(R.string.deserialize_confirmation_message);

        deserializeConfirmation.setPositiveButton(R.string.dialog_reset, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deserialize();
            }
        });

        deserializeConfirmation.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        deserializeConfirmation.create();
        deserializeConfirmation.show();
    }

    public void deserialize() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_READ_EXTERNAL_STORAGE);
            }

            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File file = new File(root, "database.ser");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            DatabaseHelper db = new DatabaseHelper(this);

            List<User> users = (ArrayList<User>) in.readObject();
            List<Account> accounts = (ArrayList<Account>) in.readObject();
            List<String> passwords = (ArrayList<String>) in.readObject();
            HashMap<Customer, List<Account>> userAccounts = (HashMap<Customer, List<Account>>) in.readObject();

            for (Account account : accounts) {
                db.insertAccount(account.getName(), account.getBalance(), account.getType());
            }

            int password_index = 0;
            for (User user : users) {
                db.insertNewUser(user.getName(), user.getAge(), user.getAddress(),
                        user.getRoleId(), "Temp pass");
                db.updateUserPassword(passwords.get(password_index), user.getId());
                password_index++;
            }

            for (Customer customer : userAccounts.keySet()) {
                for (Account account : userAccounts.get(customer)) {
                    db.insertUserAccount(customer.getId(), account.getId());
                }
            }


            in.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "database.ser File Not Found", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Object Version Not In Sync", Toast.LENGTH_SHORT).show();
        }
    }
}
