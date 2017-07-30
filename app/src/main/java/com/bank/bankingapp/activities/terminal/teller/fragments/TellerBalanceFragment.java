package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.TellerTerminal;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class TellerBalanceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_teller_check_balance, container, false);
    }

    public void tellerCheckBalance(){
        TellerTerminal tt = new TellerTerminal(this.getContext());
        TextView balance = (TextView) getView().findViewById(R.id.teller_balance);
        balance.setText(tt.addAllBalances().toString());
    }
}
