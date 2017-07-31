package com.bank.bankingapp.activities.terminal.teller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.bank.bankingapp.R;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.User;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerAuthenticateCustomerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teller_login_customer);
    }

    public void tellerLogInCustomer(View view) {
        Intent intent = new Intent(this, TellerActivity.class);
        DatabaseHelper dbh = new DatabaseHelper(this);
        TellerTerminal tt = new TellerTerminal(this);

        EditText customerIdField = (EditText) findViewById(R.id.teller_login_id);
        int userId = Integer.parseInt(customerIdField.getText().toString());

        EditText passwordField =  (EditText) findViewById(R.id.teller_login_password);
        String password = passwordField.getText().toString();

        if (tt.logIn(userId, password)){
            User usr = tt.getCurrentUser();
            intent.putExtra("user", tt.getCurrentUser());
            intent.putExtra("userId", userId);
            startActivity(intent);
        }
    }
}
