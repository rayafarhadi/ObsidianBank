package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.AdminActivity;
import com.bank.bankingapp.activities.terminal.teller.TellerActivity;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.AccountTypes;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.Admin;
import com.bank.bankingapp.user.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SendMessageFragment extends Fragment {
    private User targetUser;

    /**
     * Initializes the send message activity and sets the layout for the screen
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_admin_send_message, container, false);
    }

    /**
     * Creates a view for sending messages
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final DatabaseHelper db = new DatabaseHelper(this.getContext());
        final AdminTerminal at = new AdminTerminal(this.getContext());


        // Get users to put into Spinner
        List<String> users;
        if (this.getActivity() instanceof AdminActivity) {
            users = getUserType(0, db);
        }else {
            users = getUserType(Bank.rolesMap.get(Roles.CUSTOMER).getId() , db);
        }

        Spinner spinner = view.findViewById(R.id.admin_send_message_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_admin_balance_item, users);

        // Select a recipient
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                targetUser = db.getUsers().get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Send message
        Button send_button = view.findViewById(R.id.admin_send_message_button);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText message_field = getView().findViewById(R.id.admin_message_field);
                String text = message_field.getText().toString();
                if (text.length() == 0) {
                    Toast toast = Toast.makeText(getContext(), "Empty Field", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    at.leaveMessage(targetUser.getId(), message_field.getText().toString());
                    message_field.setText("");
                }
            }
        });

    }

    /**
     * Returns a list of all user's if typeId is 0, otherwise return the users of typeId
     * @param typeId role id to look for
     * @param db database helpers
     * @return
     */
    public List<String> getUserType(int typeId, DatabaseHelper db) {
        List<String> res = new ArrayList<>();
        for (User user : db.getUsers()) {
            if (typeId == 0) {
                res.add(user.getId() + ": " + user.getName());
            } else if (user.getRoleId() == typeId) {
                res.add(user.getId() + ": " + user.getName());
            }
        }
        return res;
    }
}