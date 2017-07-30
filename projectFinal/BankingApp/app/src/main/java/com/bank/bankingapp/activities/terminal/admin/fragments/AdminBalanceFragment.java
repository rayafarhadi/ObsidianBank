package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.AdminTerminal;

public class AdminBalanceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_admin_balance, container, false);
    }

    public void updateBalance() {
        AdminTerminal at = new AdminTerminal(this.getContext());
        TextView balance = (TextView) getView().findViewById(R.id.balance);
        balance.setText(at.addAllBalances().toString());
    }
}
