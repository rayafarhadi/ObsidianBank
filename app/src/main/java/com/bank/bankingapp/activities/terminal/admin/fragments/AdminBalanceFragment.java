package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.AdminTerminal;

public class AdminBalanceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AdminTerminal adminTerminal = new AdminTerminal();

        // Show total balance of all accounts
        System.out.println(
                "The total balance of all accounts is: " + adminTerminal.addAllBalances().toString());

        return inflater.inflate(R.layout.view_admin_balance, container, false);
    }
}
