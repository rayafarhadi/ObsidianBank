package com.bank.bankingapp.activities.login.reset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.login.LoginActivity;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseDriverA;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.Roles;

public class ResetDatabaseActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
    }

    public void reset(View view) {
        Bank.createMaps(this);

        EditText name = (EditText) findViewById(R.id.reset_admin_name);
        EditText age = (EditText) findViewById(R.id.reset_admin_age);
        EditText address = (EditText) findViewById(R.id.reset_admin_address);
        EditText password = (EditText) findViewById(R.id.reset_admin_password);

        DatabaseDriverA driver = new DatabaseDriverA(this);
        DatabaseHelper db = new DatabaseHelper(this);

        //Set versions to 1 since the database is being reset
        driver.onUpgrade(driver.getReadableDatabase(), 1, 1);

        //Create admin
        db.insertNewUser(name.getText().toString(), Integer.parseInt(age.getText().toString()),
                address.getText().toString(), Bank.rolesMap.get(Roles.ADMIN).getId(),
                password.getText().toString());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.reset_confirmation_title);
        dialogBuilder.setMessage(R.string.reset_confirmation_message);
        dialogBuilder.create();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
