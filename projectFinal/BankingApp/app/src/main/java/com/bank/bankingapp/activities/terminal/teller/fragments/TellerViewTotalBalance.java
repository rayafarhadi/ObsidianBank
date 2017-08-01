package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.teller.TellerActivity;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.terminals.TellerTerminal;

public class TellerViewTotalBalance extends Fragment {

    TextView balance;
    Context context;

    /**
     * Initializes the balance activity for admin and sets the layout of the screen
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_teller_balance, container, false);
    }

    /**
     * Creates the view for the balance activity and displays the total balance current user
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balance = view.findViewById(R.id.teller_user_balance);
        TellerActivity prevActivity = (TellerActivity) this.getActivity();
        TellerTerminal tt = (TellerTerminal) prevActivity.getAtm();
        AdminTerminal at = new AdminTerminal(context);
//        try {
            balance.setText(at.addBalances(tt.getCurrentUser().getId()).toString());
//        } catch (Exception e){
//            balance.setText("0");
//        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
