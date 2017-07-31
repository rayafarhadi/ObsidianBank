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
import com.bank.bankingapp.activities.terminal.atm.TellerActivity;
import com.bank.bankingapp.activities.terminal.teller.TellerStartingMenuActivity;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseDriverA;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.terminals.ATM;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.terminals.TellerTerminal;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Button logInButton;
    EditText idField;
    EditText passwordField;
    DatabaseHelper db;
    DatabaseDriverA driver;

    /**
     * initializes the login activity and sets the layout of the screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bank.createMaps(this);
        driver = new DatabaseDriverA(this);
        logInButton = (Button) findViewById(R.id.id_log_in_button);
        idField = (EditText) findViewById(R.id.login_id);
        passwordField = (EditText) findViewById(R.id.login_password);
        db = new DatabaseHelper(this);
    }

    /**
     * Prompts users for an id and a password and will log in the user depending on its type
     * @param view
     */
    public void logIn(View view) {
        // get user id and password
        int userId = Integer.parseInt(idField.getText().toString());
        String password = passwordField.getText().toString();

        idField.setText("");
        passwordField.setText("");
        //check if the user is an admin and log the admin into admin terminal
        if (db.getUserRole(userId) == Bank.rolesMap.get(Roles.ADMIN).getId()) {
            Intent intent = new Intent(this, AdminActivity.class);
            AdminTerminal at = new AdminTerminal(this);

            at.logIn(userId, password);

            intent.putExtra("user", at.getCurrentUser());

            if (at.getCurrentUser().authenticate(password, this)) {
                startActivity(intent);
            }
        // check if user is a teller and log the teller into teller terminal
        } else if (db.getUserRole(userId) == Bank.rolesMap.get(Roles.TELLER).getId()) {
            Intent intent = new Intent(this, TellerStartingMenuActivity.class);
            TellerTerminal tt = new TellerTerminal(this);

            tt.logIn(userId, password);

            if (tt.getCurrentUser().authenticate(password, this)) {
                startActivity(intent);
            }
        //check if the user is a customer and log the customer into ATM
        } else if (db.getUserRole(userId) == Bank.rolesMap.get(Roles.CUSTOMER).getId()) {
            Intent intent = new Intent(this, TellerActivity.class);
            ATM atm = new ATM(this);

            atm.logIn(userId, password);

            intent.putExtra("user", atm.getCurrentUser());


            if (atm.getCurrentUser().authenticate(password, this)) {
                startActivity(intent);
            }
        }
    }

    public void goToReset(View view) {
        Intent intent = new Intent(this, ResetDatabaseActivity.class);
        startActivity(intent);
    }

}

