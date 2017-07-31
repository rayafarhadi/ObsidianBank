package com.bank.bankingapp.activities.terminal.teller.fragments;

import java.math.BigDecimal;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.TellerTerminal;


public class TellerCreateAccountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_teller_create_account, container, false);
    }

    public void tellerCreateAccount (View view){
        TellerTerminal tt = new TellerTerminal(this.getContext());

        EditText nameField = (EditText) getView().findViewById(R.id.teller_create_account_name);
        String name = nameField.getText().toString();

        EditText balanceField = (EditText) getView().findViewById(R.id.teller_create_account_balance);
        BigDecimal balance = new BigDecimal(nameField.getText().toString());

        EditText typeField = (EditText) getView().findViewById(R.id.teller_Create_account_type);
        long type = Long.parseLong(nameField.getText().toString());

        int accountId = tt.makeNewAccount(name, balance, type);

        Toast t = Toast.makeText(this.getContext(), "AccountId = " + accountId, Toast.LENGTH_LONG);
        t.show();
    }
}
