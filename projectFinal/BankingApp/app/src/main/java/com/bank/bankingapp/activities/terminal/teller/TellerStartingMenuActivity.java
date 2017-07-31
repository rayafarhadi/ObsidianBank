package com.bank.bankingapp.activities.terminal.teller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.login.LoginActivity;

public class TellerStartingMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teller_starting_menu);
    }

    public void goToAuthenticateCustomer(View view) {
        Intent intent = new Intent(this, TellerAuthenticateCustomerActivity.class);
        startActivity(intent);
    }

    public void goToCreateCustomer(View view) {
        Intent intent = new Intent(this, TellerCreateCustomerActivity.class);
        startActivity(intent);
    }

    public void goToMessages(View view) {
        Intent intent = new Intent(this, TellerMessagesActivity.class);
        intent.putExtra("user", getIntent().getSerializableExtra("user"));
        startActivity(intent);
    }

    public void teller_logOut(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
