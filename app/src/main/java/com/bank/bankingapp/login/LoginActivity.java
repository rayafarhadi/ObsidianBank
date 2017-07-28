package com.bank.bankingapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminal.admin.AdminActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.id_login_form);
        logInButton = (Button) findViewById(R.id.id_log_in_button);
    }

    public void logIn(View view) {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

}

