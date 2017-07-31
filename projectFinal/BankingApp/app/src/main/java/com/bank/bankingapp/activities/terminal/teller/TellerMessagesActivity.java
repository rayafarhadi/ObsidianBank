package com.bank.bankingapp.activities.terminal.teller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminMessagesAdapter;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.User;

import java.util.ArrayList;
import java.util.List;


public class TellerMessagesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_messages);

        TellerTerminal tt = new TellerTerminal(this);

        tt.setCurrentUser((User) getIntent().getSerializableExtra("user"));

        DatabaseHelper db = new DatabaseHelper(this);
        List<Message> messages = new ArrayList<>();
        // Get unread then read messages
        for (Message message : db.getAllMessages(tt.getCurrentUser().getId())) {
            if (!message.isViewed()) {
                messages.add(message);
            }
        }
        for (Message message : db.getAllMessages(tt.getCurrentUser().getId())) {
            if (message.isViewed()) {
                messages.add(message);
            }
        }

        AdminMessagesAdapter adapter = new AdminMessagesAdapter(this, messages);
        ListView usersInfo = (ListView) findViewById(R.id.messages);
        usersInfo.setAdapter(adapter);
    }

}
