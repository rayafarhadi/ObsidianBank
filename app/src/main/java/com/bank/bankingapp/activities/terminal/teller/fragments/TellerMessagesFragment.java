package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminMessagesAdapter;
import com.bank.bankingapp.activities.terminal.teller.TellerActivity;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.terminals.TellerTerminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayafarhadi on 31/07/17.
 */

public class TellerMessagesFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TellerActivity prevActivity = (TellerActivity) this.getActivity();
        TellerTerminal tt = prevActivity.getTt();
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Message> messages = new ArrayList<>();
        // Get unread then read messages
        for (Message message : db.getAllMessages(tt.getCurrentUser().getId())){
            if (!message.isViewed()){
                messages.add(message);
            }
        }
        for (Message message : db.getAllMessages(tt.getCurrentUser().getId())){
            if (message.isViewed()){
                messages.add(message);
            }
        }

        AdminMessagesAdapter adapter = new AdminMessagesAdapter(this.getContext(), messages);
        ListView usersInfo = getView().findViewById(R.id.messages);
        usersInfo.setAdapter(adapter);
    }
}
