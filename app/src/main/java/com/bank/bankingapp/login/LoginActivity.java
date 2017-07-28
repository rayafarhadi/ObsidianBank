package com.bank.bankingapp.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bank.bankingapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.id_login_form);
    }
}

