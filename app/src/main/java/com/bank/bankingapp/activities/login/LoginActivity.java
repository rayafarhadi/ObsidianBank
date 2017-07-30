package com.bank.bankingapp.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.admin.AdminActivity;
import com.bank.bankingapp.activities.terminal.atm.ATMActivity;
import com.bank.bankingapp.activities.terminal.teller.TellerStartingMenuActivity;
import com.bank.bankingapp.database.DatabaseHelper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Button logInButton;
    EditText idField;
    EditText passwordField;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.id_login_form);
        logInButton = (Button) findViewById(R.id.id_log_in_button);
        idField = (EditText) findViewById(R.id.login_id);
        passwordField = (EditText) findViewById(R.id.login_password);
        db = new DatabaseHelper(this);
    }

    public void logIn(View view) {
        if (db.getUserRole(Integer.parseInt(idField.getText().toString())) == Bank.) {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        } else if (idField.getText().toString().equals("2")) {
            Intent intent = new Intent(this, TellerStartingMenuActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ATMActivity.class);
            startActivity(intent);
        }
    }

}

