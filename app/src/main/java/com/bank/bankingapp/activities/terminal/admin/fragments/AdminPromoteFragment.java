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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.user.Admin;
import com.bank.bankingapp.user.User;

import java.util.ArrayList;

public class AdminPromoteFragment extends Fragment {

    Spinner spinner;
    int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.view_admin_promote_teller, container, false);

        // Inflate the layout for this fragment
        return ll;
    }

    public void adminPromote() {
        AdminTerminal at = new AdminTerminal(this.getContext());

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = (Spinner) view.findViewById(R.id.admin_promote_teller_spinner);

        DatabaseHelper db = new DatabaseHelper(this.getContext());
        ArrayList<String> users = new ArrayList<>();

        for (User user : db.getUsers()) {
            if (user.getRoleId() == Bank.rolesMap.get(Roles.TELLER).getId()) {
                users.add(user.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_admin_balance_item, users);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setFields(i);
                position = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setFields(int i) {
        DatabaseHelper db = new DatabaseHelper(this.getContext());
        ArrayList<User> users = new ArrayList<>();
        AdminTerminal at = new AdminTerminal(getContext());

        for (User user : db.getUsers()) {
            if (user.getRoleId() == Bank.rolesMap.get(Roles.TELLER).getId()) {
                users.add(user);
            }
        }

        TextView id = (TextView) getView().findViewById(R.id.promote_id);
        TextView age = (TextView) getView().findViewById(R.id.promote_age);
        TextView address = (TextView) getView().findViewById(R.id.promote_address);

        id.setText("ID: " + String.valueOf(users.get(i).getId()));
        age.setText("Age: " + String.valueOf(users.get(i).getAge()));
        address.setText("Address: " + users.get(i).getAddress());
    }

    public void promote(Context context, Admin admin) {
        DatabaseHelper db = new DatabaseHelper(context);
        ArrayList<User> users = new ArrayList<>();
        AdminTerminal at = new AdminTerminal(context);

        for (User user : db.getUsers()) {
            if (user.getRoleId() == Bank.rolesMap.get(Roles.TELLER).getId()) {
                users.add(user);
            }
        }

        at.setCurrentUser(admin);
        at.promoteTeller(users.get(position).getId());
    }
}
