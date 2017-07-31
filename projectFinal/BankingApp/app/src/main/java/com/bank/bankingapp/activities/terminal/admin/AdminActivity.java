package com.bank.bankingapp.activities.terminal.admin;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminBalanceFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminCreateUserFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminMessagesFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminPromoteFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminViewUserFragment;
import com.bank.bankingapp.terminals.AdminTerminal;

public class AdminActivity extends AppCompatActivity {

    AdminCreateUserFragment createUserFragment;

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

    //----------------Create User----------------------------
    public void displayCreateUser(View view) {
        AdminTerminal at = new AdminTerminal(this);
        createUserFragment = new AdminCreateUserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, createUserFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void createUser(View view) {
        AdminTerminal at = new AdminTerminal(this);
        int id = at.createUser(createUserFragment.getUsername(), createUserFragment.getAge(), createUserFragment.getAddress(), createUserFragment.getType(), createUserFragment.getPassword());
    }

    //------------- Display Messages ------------------
    public void displayMessages(View view) {
        AdminMessagesFragment messagesFragment = new AdminMessagesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, messagesFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        messagesFragment.occupyList();
    }

    //----------- Display Users --------------------------
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

        balanceFragment.updateBalance();
    }

    public void displayPromote(View view) {
        AdminPromoteFragment promoteFragment = new AdminPromoteFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, promoteFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

}
