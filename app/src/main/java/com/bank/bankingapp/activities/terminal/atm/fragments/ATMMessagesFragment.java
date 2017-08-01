package com.bank.bankingapp.activities.terminal.atm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminMessagesAdapter;
import com.bank.bankingapp.activities.terminal.atm.ATMActivity;
import com.bank.bankingapp.activities.terminal.teller.TellerMessagesActivity;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.terminals.ATM;
import com.bank.bankingapp.terminals.Terminal;

import java.util.List;


public class ATMMessagesFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_messages, container, false);
    }

    /**
     * Displays a list of all messages associate with given user
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ATMActivity prevActivity = (ATMActivity) getActivity();
        Terminal atm = prevActivity.getAtm();

        List<Message> messages = TellerMessagesActivity.getMessages(atm.getCurrentUser().getId(), this.getContext());

        AdminMessagesAdapter adapter = new AdminMessagesAdapter(this.getContext(), messages);
        ListView usersInfo = getView().findViewById(R.id.messages);
        usersInfo.setAdapter(adapter);
    }
}

