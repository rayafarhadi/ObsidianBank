package com.bank.bankingapp.activities.terminal.atm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.activities.terminal.atm.ATMActivity;
import com.bank.bankingapp.terminals.ATM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ATMListAccountsBalancesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_atm_list_account_balance, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ATMActivity prevActivity = (ATMActivity) this.getActivity();
        ATM atm = prevActivity.getAtm();
        List<Account> accounts = new ArrayList<>();
        try {
            accounts = atm.listAccounts();
        } catch (SQLException e) {
            System.out.println("Error when listing accs");
            e.printStackTrace();
        }
        AccountInfoAdapter adapter = new AccountInfoAdapter(getContext(), accounts);
        ListView accountsInfo = getView().findViewById(R.id.accounts_and_balances);
        accountsInfo.setAdapter(adapter);

    }


}
