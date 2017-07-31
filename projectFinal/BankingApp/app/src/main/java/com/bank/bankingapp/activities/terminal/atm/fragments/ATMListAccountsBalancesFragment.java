package com.bank.bankingapp.activities.terminal.atm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bank.bankingapp.R;

/**
 * Created by rayafarhadi on 26/07/17.
 */

public class ATMListAccountsBalancesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.view_atm_list_account_balance, container, false);

//        ATMActivity prevActivity = (ATMActivity) this.getActivity();
//        ATM atm = prevActivity.getAtm();
//        List<Account> accounts = new ArrayList<>();
//        try {
//            accounts = atm.listAccounts();
//        } catch (SQLException e) {
//            System.out.println("Error when listing accs");
//            e.printStackTrace();
//        }
//        AccountInfoAdapter adapter = new AccountInfoAdapter(getContext(), accounts);
//        ListView accountsInfo = (ListView) ll.findViewById(R.id.accounts_and_balances);
//        accountsInfo.setAdapter(adapter);

        // Inflate the layout for this fragment
        return ll;
    }


}
