package com.bank.bankingapp.activities.terminal.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.login.LoginActivity;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminAllBalanceFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminBalanceFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminCreateUserFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminMessagesFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminPromoteFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminSendMessageFragment;
import com.bank.bankingapp.activities.terminal.admin.fragments.AdminViewUserFragment;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.user.Admin;

public class AdminActivity extends AppCompatActivity {

    AdminCreateUserFragment createUserFragment;
    AdminPromoteFragment promoteFragment;


    private AdminTerminal at = new AdminTerminal(this);

    public AdminTerminal getAt() {
        return at;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Intent intent = getIntent();
        at.setCurrentUser((Admin) intent.getSerializableExtra("user"));

        DatabaseHelper db = new DatabaseHelper(this);
        //Toast toast = Toast.makeText(this, db.getAllMessages(at.getCurrentUser().getId()).size(), Toast.LENGTH_SHORT);
        //toast.show(); //TODO remove

        createUserFragment = new AdminCreateUserFragment();

        if (findViewById(R.id.admin_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            AdminMessagesFragment messagesFragment = new AdminMessagesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.admin_fragment_container, messagesFragment).commit();
        }
    }

    //----------------Create User----------------------------
    public void displayCreateUser(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, createUserFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void createUser(View view) {
        AdminTerminal at = new AdminTerminal(this);

        EditText username = (EditText) findViewById(R.id.admin_create_name);
        boolean username_valid = !username.getText().toString().equals("");
        if (!username_valid) {
            username.setError("Please Enter A Name");
        }

        EditText age = (EditText) findViewById(R.id.admin_create_age);
        boolean age_valid = !age.getText().toString().equals("");
        if (!age_valid) {
            age.setError("Please Enter An Age");
        }

        EditText address = (EditText) findViewById(R.id.admin_create_address);
        boolean address_valid = !address.getText().toString().equals("");
        if (!address_valid) {
            address.setError("Please Enter An Address");
        }

        EditText type = (EditText) findViewById(R.id.admin_create_type);
        boolean type_valid = !type.getText().toString().equals("");
        if (!type_valid) {
            type.setError("Please Enter A Type");
        }

        EditText password = (EditText) findViewById(R.id.admin_create_password);
        boolean password_valid = !password.getText().toString().equals("");
        if (!password_valid) {
            password.setError("Please Enter A Password");
        }

        if (!username_valid || !age_valid || !address_valid || !type_valid || !password_valid) {
            return;
        }


        int userId = at.createUser(createUserFragment.getUsername(), createUserFragment.getAge(),
                createUserFragment.getAddress(), createUserFragment.getType(),
                createUserFragment.getPassword());

        //ID Notification dialog box
        final AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.dialog_create_title);
        idNotification.setMessage("Your User ID is " + userId);

        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()

        {
            public void onClick(DialogInterface dialog, int id) {
                createUserFragment.resetFields();
            }
        });

        idNotification.create();
        idNotification.show();
    }

    //------------- Display Messages ------------------
    public void displayMessages(View view) {
        AdminMessagesFragment messagesFragment = new AdminMessagesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, messagesFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Display Users --------------------------
    public void displayViewUser(View view) {
        AdminViewUserFragment viewUserFragment = new AdminViewUserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, viewUserFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Display Total Balance of All Accounts --------------------------
    public void displayTotalBalance(View view) {
        AdminAllBalanceFragment balanceFragment = new AdminAllBalanceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, balanceFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Display Total Balance of Single User-------------------------
    public void displayUserBalance(View view) {
        final AdminBalanceFragment balanceFragment = new AdminBalanceFragment();
        balanceFragment.setContext(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, balanceFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Promote a Teller to Admin -------------------------
    public void displayPromote(View view) {
        promoteFragment = new AdminPromoteFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, promoteFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void promote(View view) {
        promoteFragment.promote(this, (Admin) getIntent().getSerializableExtra("user"));
        displayPromote(view);
    }

    //----------- Send Message to User -------------------------
    public void sendMessage(View view) {
        final AdminSendMessageFragment sendMessageFragment = new AdminSendMessageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.admin_fragment_container, sendMessageFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //----------- Log out Admin Terminal-------------------------
    public void logOut(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
