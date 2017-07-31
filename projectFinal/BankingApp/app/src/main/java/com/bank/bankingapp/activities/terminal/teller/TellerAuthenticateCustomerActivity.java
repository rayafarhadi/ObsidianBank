package com.bank.bankingapp.activities.terminal.teller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.TellerTerminal;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerAuthenticateCustomerActivity extends AppCompatActivity {

    EditText customerIdField;
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teller_login_customer);

        customerIdField = (EditText) findViewById(R.id.teller_login_id);
        passwordField = (EditText) findViewById(R.id.teller_login_password);
    }

    public void tellerLogInCustomer(View view) {
        Intent intent = new Intent(this, TellerActivity.class);
        TellerTerminal tt = new TellerTerminal(this);

        if (!validate()) {
            return;
        }

        int userId = Integer.parseInt(customerIdField.getText().toString());
        String password = passwordField.getText().toString();

        if (tt.logIn(userId, password)) {
            intent.putExtra("user", tt.getCurrentUser());
            intent.putExtra("userId", userId);
            startActivity(intent);
        } else {
            customerIdField.setError("Incorrect ID or Password");
            passwordField.setError("Incorrect ID or Password");
        }
    }

    public boolean validate() {
        boolean valid = true;

        String id = customerIdField.getText().toString();

        String password = passwordField.getText().toString();

        if (id.isEmpty() || password.isEmpty()) {
            customerIdField.setError("Incorrect ID or Password");
            passwordField.setError("Incorrect ID or Password");
            valid = false;
        }

        return valid;
    }
}
