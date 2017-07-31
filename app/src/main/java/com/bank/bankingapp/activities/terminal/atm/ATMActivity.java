package com.bank.bankingapp.activities.terminal.atm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.login.LoginActivity;
import com.bank.bankingapp.activities.terminal.atm.fragments.ATMDepositFragment;
import com.bank.bankingapp.activities.terminal.atm.fragments.ATMListAccountsBalancesFragment;
import com.bank.bankingapp.activities.terminal.atm.fragments.ATMMessagesFragment;
import com.bank.bankingapp.activities.terminal.atm.fragments.ATMWithdrawFragment;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.terminals.ATM;
import com.bank.bankingapp.user.User;

public class ATMActivity extends AppCompatActivity {
    protected ATM atm = new ATM(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);
        Intent intent = getIntent();
        atm.setCurrentUser((User) intent.getSerializableExtra("user"));


        if (findViewById(R.id.atm_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            ATMListAccountsBalancesFragment listAccountsBalancesFragment = new ATMListAccountsBalancesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.atm_fragment_container, listAccountsBalancesFragment).commit();
        }
    }

    public void displayATMListAccountsBalances(View view) {
        ATMListAccountsBalancesFragment listAccountsBalancesFragment = new ATMListAccountsBalancesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DatabaseHelper db = new DatabaseHelper(this);
        atm.setCurrentUser(db.getUserDetails(atm.getCurrentUser().getId()));

        transaction.replace(R.id.atm_fragment_container, listAccountsBalancesFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayATMWithdraw(View view) {
        ATMWithdrawFragment withdrawFragment = new ATMWithdrawFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, withdrawFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayATMDeposit(View view) {
        ATMDepositFragment depositFragment = new ATMDepositFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, depositFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayATMMessages(View view) {
        ATMMessagesFragment atmMessagesFragment = new ATMMessagesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.atm_fragment_container, atmMessagesFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Log out ATM -------------------------
    public void logOut(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public ATM getAtm() {
        return atm;
    }
}
