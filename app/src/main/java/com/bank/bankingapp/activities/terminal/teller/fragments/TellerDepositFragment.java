package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bank.bankingapp.R;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.IllegalAmountException;
import com.bank.bankingapp.terminals.TellerTerminal;

import java.math.BigDecimal;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerDepositFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_deposit, container, false);
    }

    public void tellerDeposit () {
        TellerTerminal tt = new TellerTerminal(this.getContext());

        EditText accountIdField = (EditText) getView().findViewById(R.id.teller_deposit_account_id);
        int accountId = Integer.parseInt(accountIdField.getText().toString());

        EditText amountField = (EditText) getView().findViewById(R.id.teller_deposit_amount);
        BigDecimal amount = new BigDecimal(amountField.getText().toString());

        try {
            tt.makeDeposit(amount, accountId);
        } catch (IllegalAmountException e) {
            System.out.println("Illegal amount Exception");
        } catch (ConnectionFailedException e){
            System.out.println("Connection to database failed.");
        }
    }
}
