package com.bank.bankingapp.activities.terminal.atm.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.activities.terminal.atm.ATMActivity;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.IllegalAmountException;
import com.bank.bankingapp.generics.AccountTypes;
import com.bank.bankingapp.terminals.ATM;
import com.bank.bankingapp.user.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;


public class ATMDepositFragment extends Fragment {
    private Spinner spinner;
    private Account targetAccount;

    /**
     * Creates the view of the activity
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_deposit, container, false);
    }

    /**
     * Sets up a spinner to select and account, and a button that will deposit amount in text field to
     * that account.
     *
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ATMActivity prevActivity = (ATMActivity) this.getActivity();
        final ATM atm = (ATM) prevActivity.getAtm();
        spinner = view.findViewById(R.id.deposit_account_spinner);

        DatabaseHelper db = new DatabaseHelper(this.getContext());
        ArrayList<String> accounts = new ArrayList<>();
        // Create list of accounts in spinner
        for (Account account : ((Customer) atm.getCurrentUser()).getAccounts()) {
            accounts.add(account.getName() + ": " + account.getBalance().toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_admin_balance_item, accounts);

        // Select account
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                targetAccount = ((Customer) atm.getCurrentUser()).getAccounts().get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Deposit to account
        Button deposit_button = view.findViewById(R.id.deposit_button);
        deposit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText message_field = getView().findViewById(R.id.deposit_amount);
                final AlertDialog.Builder idNotification = new AlertDialog.Builder(getContext());
                idNotification.setTitle(R.string.dialog_create_title);

                String text = message_field.getText().toString();
                if (text.length() == 0) {
                    message_field.setError("Enter Dollar Amount To Deposit");
                } else {
                    try {
                        atm.makeDeposit(new BigDecimal(text), targetAccount.getId());
                        if (targetAccount.getType() == Bank.accountsMap.get(AccountTypes.RSA).getId()) {
                            idNotification.setTitle("Restricted Savings Account");
                            idNotification.setMessage("Money is not allowed to be deposited into this account using an atm.");
                        } else {
                            idNotification.setMessage(text + "$ has been successfully deposited into from account.");
                        }
                    } catch (IllegalAmountException e) {
                        idNotification.setMessage("Cannot deposit a negative amount.");
                    } catch (ConnectionFailedException e) {
                        idNotification.setMessage("Error: Connection to database failed.");
                    }
                    idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    message_field.setText("");
                    idNotification.create();
                    idNotification.show();
                }
            }
        });
    }
}
