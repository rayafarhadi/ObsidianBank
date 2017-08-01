package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.user.Customer;
import com.bank.bankingapp.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AdminBalanceFragment extends Fragment {

    TextView balance;
    Context context;

    /**
     * Initializes the balance activity for admin and sets the layout of the screen
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_admin_balance, container, false);
    }

    /**
     * Creates the view for the balance activity and displays the balance of specific users
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balance = view.findViewById(R.id.admin_user_balance);

        Spinner spinner = view.findViewById(R.id.admin_balance_spinner);

        DatabaseHelper db = new DatabaseHelper(this.getContext());
        ArrayList<String> users = new ArrayList<>();

        for (User user : db.getUsers()) {
            if (user.getRoleId() == Bank.rolesMap.get(Roles.CUSTOMER).getId()) {
                users.add(user.getId() + ": " + user.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_admin_balance_item, users);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateBalance(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * updates the balance of the user
     * @param i
     */
    public void updateBalance(int i) {
        AdminTerminal at = new AdminTerminal(context);
        DatabaseHelper db = new DatabaseHelper(context);
        ArrayList<User> users = db.getUsers();

        ArrayList<Customer> customers = new ArrayList<>();
        for (User user : users) {
            if (user.getRoleId() == Bank.rolesMap.get(Roles.CUSTOMER).getId()) {
                customers.add((Customer) user);
            }
        }

        BigDecimal total_balance = at.addBalances(customers.get(i).getId());
        if (total_balance == null) {
            total_balance = new BigDecimal(0);
        }

        balance.setText(total_balance.toString());
    }
}
