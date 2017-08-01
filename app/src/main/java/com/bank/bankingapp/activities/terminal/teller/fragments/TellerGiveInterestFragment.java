package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.activities.terminal.teller.TellerActivity;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.Customer;

import java.util.ArrayList;
import java.util.List;


public class TellerGiveInterestFragment extends Fragment {
    private Spinner spinner;
    private Account targetAccount;

    /**
     * Inflates the current layout to fill in fragment.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_teller_give_interest, container, false);
    }


    /**
     * Creates a view for giving interest to either a selected account, or all accounts once current layout is
     * inflated.
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TellerActivity prevActivity = (TellerActivity) this.getActivity();
        final TellerTerminal tt = (TellerTerminal) prevActivity.getAtm();

        // Get users to put into Spinner
        List<String> accounts = new ArrayList<>();

        for (Account account : ((Customer) tt.getCurrentUser()).getAccounts()) {
            accounts.add(account.getId() + ": " + account.getName() + "   " + account.getBalance());
        }
        spinner = view.findViewById(R.id.teller_give_interest_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_admin_balance_item, accounts);

        // Select a recipient
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                targetAccount = ((Customer) tt.getCurrentUser()).getAccounts().get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Send message
        Button send_button = view.findViewById(R.id.teller_give_interest_button);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.giveInterest(targetAccount.getId());
            }
        });

    }
}
