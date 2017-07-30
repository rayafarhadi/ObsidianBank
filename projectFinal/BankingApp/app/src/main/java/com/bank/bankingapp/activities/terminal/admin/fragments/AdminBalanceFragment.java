package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bank.bankingapp.R;

import java.math.BigDecimal;

public class AdminBalanceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Check the balance of account with account id
        System.out.println(
                "\nPlease enter the amount id of the account you wish to check the balance of.");
        System.out.print("ACCOUNT ID: ");
        int accountIdCheck = getValidInt(br);
        try {
            BigDecimal balance = atm.checkBalance(accountIdCheck);
            System.out.println("The balance of account is " + balance.toString());
        } catch (ConnectionFailedException e) {
            System.out
                    .println("The account with id" + accountIdCheck + " does not belong to user.");
        }
        break;

        return inflater.inflate(R.layout.view_admin_balance, container, false);
    }
}
