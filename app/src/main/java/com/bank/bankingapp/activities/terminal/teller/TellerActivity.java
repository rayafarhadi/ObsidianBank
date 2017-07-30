package com.bank.bankingapp.activities.terminal.teller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerBalanceFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerCreateAccountFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerDepositFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerGiveInterestFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerWithdrawFragment;

/**
 * Created by rayafarhadi on 26/07/17.
 */

public class TellerActivity extends AppCompatActivity {
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
    }

    public void displayCreateAccount(View view) {
        TellerCreateAccountFragment createAccountFragment = new TellerCreateAccountFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, createAccountFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayGiveInterest(View view) {
        TellerGiveInterestFragment giveInterestFragment = new TellerGiveInterestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, giveInterestFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayTellerDeposit(View view) {
        TellerDepositFragment depositFragment = new TellerDepositFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, depositFragment);
        transaction.addToBackStack(null);

        transaction.commit();
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