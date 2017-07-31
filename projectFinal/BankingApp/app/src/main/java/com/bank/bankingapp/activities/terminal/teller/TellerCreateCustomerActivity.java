package com.bank.bankingapp.activities.terminal.teller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.TellerTerminal;

import org.w3c.dom.Text;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerCreateCustomerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teller_create_customer);
    }

    public void tellerCreateCustomer(View view) {
        Intent intent = new Intent(this, TellerAuthenticateCustomerActivity.class);
        startActivity(intent);

        TextView nameField = (TextView) findViewById(R.id.teller_create_name);
        String name = nameField.getText().toString();

        TextView ageField = (TextView) findViewById(R.id.teller_create_age);
        int age = Integer.parseInt(ageField.getText().toString());

        TextView addressField = (TextView) findViewById(R.id.teller_create_address);
        String address = addressField.getText().toString();

        TextView passwordField = (TextView) findViewById(R.id.teller_create_password);
        String password = passwordField.getText().toString();

        TellerTerminal tt = new TellerTerminal(this);
        int customerId = tt.makeNewUser(name, age, address, password);

        Toast t = Toast.makeText(this, "userId = " + customerId, Toast.LENGTH_LONG);
        t.show();
    }
}
