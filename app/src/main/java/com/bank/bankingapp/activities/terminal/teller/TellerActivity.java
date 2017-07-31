package com.bank.bankingapp.activities.terminal.teller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerBalanceFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerCreateAccountFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerDepositFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerGiveInterestFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerWithdrawFragment;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.IllegalAmountException;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.User;

import java.math.BigDecimal;

/**
 * Created by rayafarhadi on 26/07/17.
 */

public class TellerActivity extends AppCompatActivity {

    TellerTerminal tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teller);

        if (findViewById(R.id.teller_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            TellerCreateAccountFragment createAccountFragment = new TellerCreateAccountFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.teller_fragment_container, createAccountFragment).commit();
        }
        tt = new TellerTerminal(this);
        User currentCustomer;
        currentCustomer = (User)getIntent().getSerializableExtra("user");
        currentCustomer.setId(getIntent().getIntExtra("userId", 0));
        tt.setCurrentUser(currentCustomer);
    }

    public void displayCreateAccount(View view) {
        TellerCreateAccountFragment createAccountFragment = new TellerCreateAccountFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, createAccountFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void createAccount (View view){
        EditText nameField = (EditText) findViewById(R.id.teller_create_account_name);
        String name = nameField.getText().toString();

        EditText balanceField = (EditText) findViewById(R.id.teller_create_account_balance);
        BigDecimal balance = new BigDecimal(balanceField.getText().toString());

        EditText typeField = (EditText) findViewById(R.id.teller_Create_account_type);
        long type = Long.parseLong(typeField.getText().toString());

        int accountId = tt.makeNewAccount(name, balance, type);

        Toast t = Toast.makeText(this, "AccountId = " + accountId, Toast.LENGTH_LONG);
        t.show();
    }

    public void displayGiveInterest(View view) {
        TellerGiveInterestFragment giveInterestFragment = new TellerGiveInterestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, giveInterestFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void tellerGiveInterestAll(View view){
        tt.giveInterestAll();
        final AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.dialog_create_title);
        idNotification.setMessage("Interest has been applied to all accounts.");
        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        idNotification.create();
        idNotification.show();
        tt.giveInterestAll();

    }

    public void displayTellerDeposit(View view) {
        TellerDepositFragment depositFragment = new TellerDepositFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, depositFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void tellerDeposit (View view) {
        TellerTerminal tt = new TellerTerminal(this);

        EditText accountIdField = (EditText) findViewById(R.id.teller_deposit_account_id);
        int accountId = Integer.parseInt(accountIdField.getText().toString());

        EditText amountField = (EditText) findViewById(R.id.teller_deposit_amount);
        BigDecimal amount = new BigDecimal(amountField.getText().toString());

        try {
            tt.makeDeposit(amount, accountId);
        } catch (IllegalAmountException e) {
            System.out.println("Illegal amount Exception");
        } catch (ConnectionFailedException e){
            System.out.println("Connection to database failed.");
        }
    }

    public void displayTellerWithdraw(View view) {
        TellerWithdrawFragment withdrawFragment = new TellerWithdrawFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, withdrawFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayBalance(View view) {
        TellerBalanceFragment balanceFragment = new TellerBalanceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, balanceFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void closeSession(View view) {
        Intent intent = new Intent(this, TellerStartingMenuActivity.class);
        startActivity(intent);
    }
}