package com.bank.uitest.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bank.uitest.R;

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

