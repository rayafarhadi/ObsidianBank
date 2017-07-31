package com.bank.bankingapp.activities.terminal.atm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.terminals.ATM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class ATMBalanceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_atm_balance, container, false);
    }
}
