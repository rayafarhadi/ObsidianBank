package com.bank.bankingapp.activities.terminal.teller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bank.bankingapp.R;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.terminals.TellerTerminal;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerAuthenticateCustomerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teller_login_customer);
    }

    public void authenticateUser(View view) {
        Intent intent = new Intent(this, TellerActivity.class);
        startActivity(intent);
        DatabaseHelper dbh = new DatabaseHelper(this);
        TellerTerminal tt = new TellerTerminal(this);
    }
}
