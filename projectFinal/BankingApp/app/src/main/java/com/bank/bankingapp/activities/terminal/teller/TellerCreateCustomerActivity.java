package com.bank.bankingapp.activities.terminal.teller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.TellerTerminal;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerCreateCustomerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teller_create_customer);
        // actionBar
        getSupportActionBar().setTitle("Create Customer");
    }

    public void tellerCreateCustomer(View view) {
        boolean valid = true;

        TextView nameField = (TextView) findViewById(R.id.teller_create_name);

        if (nameField.getText().length() == 0) {
            nameField.setError("Enter The Customer's Name");
            valid = false;
        }

        TextView ageField = (TextView) findViewById(R.id.teller_create_age);

        if (ageField.getText().length() == 0) {
            ageField.setError("Enter The Customer's Age");
            valid = false;
        }

        TextView addressField = (TextView) findViewById(R.id.teller_create_address);

        if (nameField.getText().length() == 0) {
            addressField.setError("Enter The Customer's Address");
            valid = false;
        }


        TextView passwordField = (TextView) findViewById(R.id.teller_create_password);

        if (passwordField.getText().length() == 0) {
            passwordField.setError("Enter The Customer's Password");
            valid = false;
        }

        if (!valid) {
            return;
        }

        int age = Integer.parseInt(ageField.getText().toString());
        String name = nameField.getText().toString();
        String password = passwordField.getText().toString();
        String address = addressField.getText().toString();

        TellerTerminal tt = new TellerTerminal(this);
        int customerId = tt.makeNewUser(name, age, address, password);

        final Intent intent = new Intent(this, TellerAuthenticateCustomerActivity.class);

        //ID Notification dialog box
        AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle("User ID");
        idNotification.setMessage("The new User's ID is: " + customerId);
        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(intent);
            }
        });

        idNotification.create();
        idNotification.show();
    }
}
