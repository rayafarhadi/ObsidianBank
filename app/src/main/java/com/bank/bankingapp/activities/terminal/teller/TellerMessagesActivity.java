package com.bank.bankingapp.activities.terminal.teller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminMessagesAdapter;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.Teller;

import java.util.ArrayList;
import java.util.List;


public class TellerMessagesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_messages);

        TellerTerminal tt = new TellerTerminal(this);
        tt.setCurrentTeller((Teller) getIntent().getSerializableExtra("teller"));

        List<Message> messages = getMessages(tt.getCurrentTeller().getId(), this);

        AdminMessagesAdapter adapter = new AdminMessagesAdapter(this, messages);
        ListView usersInfo = (ListView) findViewById(R.id.messages);
        usersInfo.setAdapter(adapter);
    }

    public static ArrayList<Message> getMessages(int userId, Context context){
        List<Message> messages = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(context);
        // Get unread then read messages
        for (Message message : db.getAllMessages(userId)) {
            if (!message.isViewed()) {
                messages.add(message);
            }
        }
        for (Message message : db.getAllMessages(userId)) {
            if (message.isViewed()) {
                messages.add(message);
            }
        }
        return (ArrayList<Message>) messages;
    }

}
