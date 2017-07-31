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
import com.bank.bankingapp.exceptions.InsuffiecintFundsException;
import com.bank.bankingapp.terminals.TellerTerminal;

import java.math.BigDecimal;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerWithdrawFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_teller_withdraw, container, false);
    }

    public void tellerWithdraw(){
        TellerTerminal tt = new TellerTerminal(this.getContext());

        EditText accountIdField = (EditText) getView().findViewById(R.id.teller_withdraw_account_id);
        int accountId = Integer.parseInt(accountIdField.getText().toString());

        EditText amountField = (EditText) getView().findViewById(R.id.teller_withdraw_amt);
        BigDecimal amount = new BigDecimal(amountField.getText().toString());

        try{
            tt.makeWithdrawal(amount, accountId);
        } catch (InsuffiecintFundsException e){
            System.out.println("Not enough funds to withdraw.");
        } catch (IllegalAmountException e) {
            System.out.println("Cannot withdraw negative funds.");
        } catch (ConnectionFailedException e){
            System.out.println("Failed to establish connection with database.");
        }
    }
}
