package com.bank.bankingapp.activities.terminal.atm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bank.bankingapp.R;

/**
 * Created by rayafarhadi on 26/07/17.
 */

public class ATMListAccountsBalancesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.view_atm_list_account_balance, container, false);
    }

}
