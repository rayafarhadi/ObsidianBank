package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bank.bankingapp.R;

/**
 *
 * Created by rayafarhadi on 24/07/17.
 */

public class AdminOptionsFragment extends Fragment {
    /**
     * Creates options for the admin and sets the layout on the screen
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_admin_options, container, false);
    }
}
