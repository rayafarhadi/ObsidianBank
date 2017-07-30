package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.terminals.AdminTerminal;

import java.util.ArrayList;

public class AdminMessagesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_messages, container, false);
    }

    public void occupyList() {
        AdminTerminal at = new AdminTerminal();

        ArrayAdapter adapter = new ArrayAdapter(this, R.id.messages, new ArrayList<Message>);
        for (Message message : at.viewAllMessages()) {
            adapter.add(message.getMessage());
        }

        ListView messages = (ListView) getView().findViewById(R.id.messages);
        messages.setAdapter(adapter);
    }
}
