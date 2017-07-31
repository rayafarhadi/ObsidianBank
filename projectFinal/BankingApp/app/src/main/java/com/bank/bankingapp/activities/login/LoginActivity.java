package com.bank.bankingapp.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.login.reset.ResetDatabaseActivity;
import com.bank.bankingapp.activities.terminal.admin.AdminActivity;
import com.bank.bankingapp.activities.terminal.atm.ATMActivity;
import com.bank.bankingapp.activities.terminal.teller.TellerStartingMenuActivity;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseDriverA;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.terminals.ATM;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.terminals.Terminal;

import java.io.Serializable;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Button logInButton;
    EditText idField;
    EditText passwordField;
    DatabaseHelper db;
    DatabaseDriverA driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        driver = new DatabaseDriverA(this);
        logInButton = (Button) findViewById(R.id.id_log_in_button);
        idField = (EditText) findViewById(R.id.login_id);
        passwordField = (EditText) findViewById(R.id.login_password);
        db = new DatabaseHelper(this);
    }

    public void logIn(View view) {
        int userId = Integer.parseInt(idField.getText().toString());
        String password = passwordField.getText().toString();

        if (db.getUserRole(userId) == Bank.rolesMap.get(Roles.ADMIN).getId()) {
            Intent intent = new Intent(this, AdminActivity.class);
            AdminTerminal at = new AdminTerminal(this.getBaseContext());
            intent.putExtra("terminal", at);
            if (at.logIn(userId, password)) {
                startActivity(intent);
            }

        } else if (db.getUserRole(userId) == Bank.rolesMap.get(Roles.TELLER).getId()) {
            Intent intent = new Intent(this, TellerStartingMenuActivity.class);
            TellerTerminal tt = new TellerTerminal(this.getBaseContext());
            intent.putExtra("terminal", tt);
            if (tt.logIn(userId, password)) {
                startActivity(intent);
            }

        } else if (db.getUserRole(userId) == Bank.rolesMap.get(Roles.CUSTOMER).getId()) {
            Intent intent = new Intent(this, ATMActivity.class);
            ATM atm = new ATM(this.getBaseContext());
            intent.putExtra("terminal", atm);
            if (atm.logIn(userId, password)) {
                startActivity(intent);
            }
        }
    }
    public void goToReset(View view) {
        Intent intent = new Intent(this, ResetDatabaseActivity.class);
        startActivity(intent);
    }

}

