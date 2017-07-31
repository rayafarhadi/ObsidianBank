package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.user.Customer;
import com.bank.bankingapp.user.User;

import java.util.ArrayList;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerBalanceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_teller_check_balance, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = view.findViewById(R.id.teller_balance_spinner);

        DatabaseHelper db = new DatabaseHelper(this.getContext());
        ArrayList<String> accounts = new ArrayList<>();
        User user = (User) getActivity().getIntent().getSerializableExtra("user");
        for (Integer id : db.getAccountIds(user.getId())) {
            accounts.add(db.getAccountDetails(id).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_teller_check_balance_item, accounts);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkBalance(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void checkBalance(int i) {
        TextView balance = (TextView) getView().findViewById(R.id.teller_balance);

        Customer user = (Customer) getActivity().getIntent().getSerializableExtra("user");

        balance.setText(user.getAccounts().get(i).getBalance().toString());
    }
}
