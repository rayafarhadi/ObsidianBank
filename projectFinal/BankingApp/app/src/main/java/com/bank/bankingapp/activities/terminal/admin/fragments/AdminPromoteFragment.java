package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.bank.bankingapp.R;
import com.bank.bankingapp.terminals.AdminTerminal;

public class AdminPromoteFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.view_admin_promote_teller, container, false);

        Spinner spinner = (Spinner) getView().findViewById(R.id.admin_promote_teller_spinner);

        //SpinnerAdapter adapter = SpinnerAdapter.createFromResource(this);

        // Inflate the layout for this fragment
        return ll;
    }

    public void adminPromote() {
        AdminTerminal at = new AdminTerminal(this.getContext());

    }
}
