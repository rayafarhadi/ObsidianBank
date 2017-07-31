package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.AdminActivity;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.terminals.AdminTerminal;

import java.util.ArrayList;
import java.util.List;

public class AdminMessagesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AdminActivity prevActivity = (AdminActivity) this.getActivity();
        AdminTerminal at = prevActivity.getAt();
        List<Message> messages = at.viewAllMessages();

        AdminMessagesAdapter adapter = new AdminMessagesAdapter(getContext(), messages);
        ListView usersInfo = getView().findViewById(R.id.messages);
        usersInfo.setAdapter(adapter);
    }
}
