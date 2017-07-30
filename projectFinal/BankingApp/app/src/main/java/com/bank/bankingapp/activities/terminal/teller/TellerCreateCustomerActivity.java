package com.bank.bankingapp.activities.terminal.teller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bank.bankingapp.R;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerCreateCustomerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_teller_create_customer);
    }

    public void create(View view) {
        Intent intent = new Intent(this, TellerAuthenticateCustomerActivity.class);
        startActivity(intent);
    }
}
