package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bank.bankingapp.R;
import com.bank.bankingapp.bank.Bank;


public class TellerCreateAccountFragment extends Fragment {

    int spinnerPos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_teller_create_account, container, false);
    }

    /**
     * creates the view for the activity and creates a new user
     *
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_admin_balance_item, new String[]{"Chequing", "Savings", "TFSA", "RSA", "BOA"});

        final Spinner type = view.findViewById(R.id.create_account_type_spinner);

        type.setAdapter(adapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPos(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setPos(int pos) {
        spinnerPos = pos;
    }

    public int getType() {
        Spinner type = getView().findViewById(R.id.create_account_type_spinner);

        for (String accountType : Bank.accountsMap.getNames()) {
            if (accountType.equalsIgnoreCase(type.getAdapter().getItem(spinnerPos).toString())) {
                return Bank.accountsMap.getNames().indexOf(accountType) + 1;
            }
        }

        return 0;
    }

    public void clearFields() {
        EditText nameField = (EditText) getView().findViewById(R.id.teller_create_account_name);
        EditText balanceField = (EditText) getView().findViewById(R.id.teller_create_account_balance);

        nameField.setText("");
        balanceField.setText("");
    }
}
