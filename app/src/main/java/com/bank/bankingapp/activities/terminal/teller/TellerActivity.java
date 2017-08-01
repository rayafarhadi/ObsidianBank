package com.bank.bankingapp.activities.terminal.teller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.atm.ATMActivity;
import com.bank.bankingapp.activities.terminal.teller.fragments.SendMessageFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerCreateAccountFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerGiveInterestFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerProjectEarningsFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerUpdateInfoFragment;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.User;

import java.math.BigDecimal;

public class TellerActivity extends ATMActivity {

    TellerCreateAccountFragment createAccountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teller);
        atm = new TellerTerminal(this);
        User currentCustomer = (User) getIntent().getSerializableExtra("user");
        atm.setCurrentUser(currentCustomer);
        // actionBar
        getSupportActionBar().setTitle("Teller Terminal");
    }

    /**
     * Fragment that displays input fields for current user to create account
     *
     * @param view
     */
    public void displayCreateAccount(View view) {
        createAccountFragment = new TellerCreateAccountFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DatabaseHelper db = new DatabaseHelper(this);
        atm.setCurrentUser(db.getUserDetails(atm.getCurrentUser().getId()));
        transaction.replace(R.id.atm_fragment_container, createAccountFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    /**
     * Creates an account for the current user
     *
     * @param view
     */
    public void createAccount(View view) {
        EditText nameField = (EditText) findViewById(R.id.teller_create_account_name);
        String name = nameField.getText().toString();

        EditText balanceField = (EditText) findViewById(R.id.teller_create_account_balance);
        BigDecimal balance = new BigDecimal(balanceField.getText().toString());

        int accountId = ((TellerTerminal) atm).makeNewAccount(name, balance, createAccountFragment.getType());

        AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.dialog_create_title);
        idNotification.setMessage("The ID for this account is: " + accountId);
        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        idNotification.create();
        idNotification.show();

        createAccountFragment.clearFields();
    }

    /**
     * Fragment that allows users to either give interest to specific, or all accounts
     *
     * @param view
     */
    public void displayGiveInterest(View view) {
        TellerGiveInterestFragment giveInterestFragment = new TellerGiveInterestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, giveInterestFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    /**
     * Gives interest to all of the current user's accounts
     *
     * @param view
     */
    public void tellerGiveInterestAll(View view) {
        ((TellerTerminal) atm).giveInterestAll();
        final AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.dialog_create_title);
        idNotification.setMessage("Interest has been applied to all accounts.");
        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        idNotification.create();
        idNotification.show();
        ((TellerTerminal) atm).giveInterestAll();

    }

    /**
     * Fragment that allows current user to update their data
     *
     * @param view
     */
    public void displayUpdateInfo(View view) {
        TellerUpdateInfoFragment updateInfoFragment = new TellerUpdateInfoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, updateInfoFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    /**
     * Updates current user's data based off input fields
     *
     * @param view
     */
    public void updateInfo(View view) {
        TextView nameField = (TextView) findViewById(R.id.teller_update_name);
        String name = nameField.getText().toString();

        if (!name.equals("")) {
            ((TellerTerminal) atm).updateName(name);
        }

        TextView addressField = (TextView) findViewById(R.id.teller_update_address);
        String address = addressField.getText().toString();

        if (!address.equals("")) {
            ((TellerTerminal) atm).updateAddress(address);
        }

        TextView passwordField = (TextView) findViewById(R.id.teller_update_password);
        String password = passwordField.getText().toString();

        if (!password.equals("")) {
            ((TellerTerminal) atm).updatePassword(password);
        }

        TextView ageField = (TextView) findViewById(R.id.teller_update_age);
        String age = ageField.getText().toString();

        if (!age.equals("")) {
            ((TellerTerminal) atm).updateAge(Integer.parseInt(age));
        }

        final AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.dialog_create_title);
        idNotification.setMessage("Account information has been updated.");
        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        idNotification.create();
        idNotification.show();

        ageField.setText("");
        passwordField.setText("");
        addressField.setText("");
        ageField.setText("");

    }

    /**
     * Displays a graph that takes into account to show project account balance in the future
     *
     * @param view
     */
    public void displayProjectedEarnings(View view) {
        TellerProjectEarningsFragment earningsFragment = new TellerProjectEarningsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, earningsFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Send Message to User -------------------------
    public void sendMessage(View view) {
        final SendMessageFragment sendMessageFragment = new SendMessageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, sendMessageFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    /**
     * Leaves user session
     *
     * @param view
     */
    public void closeSession(View view) {
        Intent intent = new Intent(this, TellerStartingMenuActivity.class);
        startActivity(intent);
    }
}