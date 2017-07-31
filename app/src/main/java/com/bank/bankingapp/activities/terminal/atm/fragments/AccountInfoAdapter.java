package com.bank.bankingapp.activities.terminal.atm.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountInfoAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Account> mDataSource;

    public AccountInfoAdapter(Context context, List<Account> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.view_atm_list_account_balance, parent, false);

        // Get title element
        TextView nameTextView = (TextView) rowView.findViewById(R.id.view_accountName);

        // Get subtitle element
        TextView typeTextView = (TextView) rowView.findViewById(R.id.view_accountType);

        // Get detail element
        TextView balanceTextView = (TextView) rowView.findViewById(R.id.view_accountBal);

        // Set the account
        Account account = (Account) getItem(position);
        nameTextView.setText(account.getName());
        typeTextView.setText(account.getType());
        balanceTextView.setText((CharSequence) account.getBalance());

        return rowView;
    }
}
