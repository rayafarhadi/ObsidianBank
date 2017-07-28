package com.bank.bankingapp.terminal.admin;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminal.admin.fragments.AdminBalanceFragment;
import com.bank.bankingapp.terminal.admin.fragments.AdminCreateUserFragment;
import com.bank.bankingapp.terminal.admin.fragments.AdminMessagesFragment;
import com.bank.bankingapp.terminal.admin.fragments.AdminPromoteFragment;
import com.bank.bankingapp.terminal.admin.fragments.AdminViewUserFragment;

/**
 * Created by rayafarhadi on 24/07/17.
 */

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (findViewById(R.id.admin_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            AdminMessagesFragment messagesFragment = new AdminMessagesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.admin_fragment_container, messagesFragment).commit();
        }
    }

    public void displayCreateUser(View view) {
        AdminCreateUserFragment createUserFragment = new AdminCreateUserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, createUserFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayMessages(View view) {
        AdminMessagesFragment messagesFragment = new AdminMessagesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, messagesFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayViewUser(View view) {
        AdminViewUserFragment viewUserFragment = new AdminViewUserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, viewUserFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayTotalBalance(View view) {
        AdminBalanceFragment balanceFragment = new AdminBalanceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, balanceFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayPromote(View view) {
        AdminPromoteFragment promoteFragment = new AdminPromoteFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, promoteFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

}
