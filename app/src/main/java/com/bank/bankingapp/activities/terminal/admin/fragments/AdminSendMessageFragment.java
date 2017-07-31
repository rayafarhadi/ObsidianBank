package com.bank.bankingapp.activities.terminal.admin.fragments;

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
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.user.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminSendMessageFragment extends Fragment {
    private User targetUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_admin_send_message, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AdminActivity prevActivity = (AdminActivity) this.getActivity();
        final AdminTerminal at = prevActivity.getAt();
        final DatabaseHelper db = new DatabaseHelper(this.getContext());


        // Get users to put into Spinner
        List<String> users = new ArrayList<>();

        for (User user : db.getUsers()) {
            users.add(user.getId() + ": " + user.getName());
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
                at.leaveMessage(targetUser.getId(), message_field.getText().toString());
                Toast toast = Toast.makeText(getContext(), db.getAllMessages(targetUser.getId()).get(15).getMessage(), Toast.LENGTH_SHORT);
                toast.show(); //TODO remove
            }
        });

    }
}