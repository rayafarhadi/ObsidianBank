package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.AdminTerminal;

import java.math.BigDecimal;

public class AdminBalanceFragment extends Fragment {

    TextView balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_admin_balance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balance = view.findViewById(R.id.admin_user_balance);
    }

    public void updateBalance() {
        AdminTerminal at = new AdminTerminal(this.getContext());
        BigDecimal total_balance = at.addAllBalances();
        if (total_balance == null) {
            total_balance = new BigDecimal(0);
        }

        balance.setText(total_balance.toString());
    }
}
