package com.bank.bankingapp.activities.terminal.teller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.AdminActivity;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminMessagesAdapter;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.messages.Message;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.terminals.Terminal;

import java.util.ArrayList;
import java.util.List;


public class TellerMessagesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_messages);
    }

}
