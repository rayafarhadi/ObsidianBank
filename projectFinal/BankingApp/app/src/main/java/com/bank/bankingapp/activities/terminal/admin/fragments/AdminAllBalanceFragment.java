package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.AdminTerminal;

import java.math.BigDecimal;

public class AdminAllBalanceFragment extends Fragment {

    TextView balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.view_admin_all_balance, container, false);
        balance = (TextView) rl.findViewById(R.id.admin_total_balance);

        AdminTerminal at = new AdminTerminal(this.getContext());
        BigDecimal total_balance = at.addAllBalances();
        if (total_balance == null) {
            total_balance = new BigDecimal(0);
        }

        balance.setText(total_balance.toString());

        return rl;
    }
}
