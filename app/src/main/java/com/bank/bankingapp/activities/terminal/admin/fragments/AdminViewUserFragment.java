package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.activities.terminal.admin.AdminActivity;
import com.bank.bankingapp.activities.terminal.atm.ATMActivity;
import com.bank.bankingapp.activities.terminal.atm.fragments.AccountInfoAdapter;
import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.Roles;
import com.bank.bankingapp.terminals.ATM;
import com.bank.bankingapp.terminals.AdminTerminal;
import com.bank.bankingapp.user.Admin;
import com.bank.bankingapp.user.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayafarhadi on 28/07/17.
 */

public class AdminViewUserFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_admin_view_users, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AdminActivity prevActivity = (AdminActivity) this.getActivity();
        AdminTerminal at = prevActivity.getAt();
        List<User> users = new ArrayList<>();
        try {
            users = at.getUserType(Bank.rolesMap.get(Roles.ADMIN).getId());
            users.addAll(at.getUserType(Bank.rolesMap.get(Roles.TELLER).getId()));
            users.addAll(at.getUserType(Bank.rolesMap.get(Roles.CUSTOMER).getId()));
        } catch (SQLException e) {
            System.out.println("Error when listing users");
            e.printStackTrace();
        }

        AdminViewUsersAdapter adapter = new AdminViewUsersAdapter(getContext(), users);
        ListView usersInfo = getView().findViewById(R.id.users);
        usersInfo.setAdapter(adapter);
    }
}
