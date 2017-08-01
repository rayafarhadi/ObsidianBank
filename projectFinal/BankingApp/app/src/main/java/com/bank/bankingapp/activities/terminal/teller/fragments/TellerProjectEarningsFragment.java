package com.bank.bankingapp.activities.terminal.teller.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
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
import com.bank.bankingapp.activities.terminal.teller.TellerActivity;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.exceptions.ConnectionFailedException;
import com.bank.bankingapp.exceptions.IllegalAmountException;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.Customer;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Joey on 7/31/2017.
 */

public class TellerProjectEarningsFragment extends Fragment {

    private Spinner spinner;
    private Account targetAccount;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.teller_projected_earnings, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TellerActivity prevActivity = (TellerActivity) this.getActivity();
        final TellerTerminal tt = (TellerTerminal) prevActivity.getAtm();;
        spinner = view.findViewById(R.id.teller_projected_earnings_spinner);

        DatabaseHelper db = new DatabaseHelper(this.getContext());
        ArrayList<String> accounts = new ArrayList<>();
        // Create list of accounts in spinner
        for (Account account : ((Customer) tt.getCurrentUser()).getAccounts()) {
            accounts.add(account.getName() + ": " + account.getBalance().toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_admin_balance_item, accounts);

        // Select account
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                targetAccount = ((Customer) tt.getCurrentUser()).getAccounts().get(i);
                populateGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void populateGraph(){
        GraphView graph = getView().findViewById(R.id.earnings_graph);
        graph.setTitle("Projected Earnings");
        graph.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        series.setDrawBackground(true);
        if (targetAccount.getBalance().longValue() >= 0){
            series.setColor(Color.parseColor("#4CAF50"));
            series.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
        else {
            series.setColor(Color.parseColor("#F44336"));
            series.setBackgroundColor(Color.parseColor("#F44336"));
        }
        double x;
        BigDecimal y_bigdecimal = targetAccount.getBalance();
        for (int i = 0; i < 13; i++){
            x = i;
            BigDecimal income = Bank.accountsMap.getInterestRates().get(targetAccount.getType() - 1).multiply(y_bigdecimal);
            y_bigdecimal = y_bigdecimal.add(income);
            series.appendData(new DataPoint(x, y_bigdecimal.doubleValue()), true, 13);
        }
        graph.getViewport().setMaxX(12);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.addSeries(series);
    }
}