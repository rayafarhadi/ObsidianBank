package com.bank.bankingapp.activities.terminal.atm.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.account.Account;
import com.bank.bankingapp.database.DatabaseHelper;

import java.util.List;

public class AccountInfoAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Account> mDataSource;

    /**
     * Initializes the account info activity and sets layout for screen
     * @param context
     * @param items
     */
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

    /**
     * Sets up a field for the account name, account type, and account balance in each list item.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DatabaseHelper db = new DatabaseHelper(mContext);
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.view_atm_list_item, parent, false);

        // Get title element
        TextView nameTextView = rowView.findViewById(R.id.view_accountName);

        // Get subtitle element
        TextView typeTextView = rowView.findViewById(R.id.view_accountType);

        // Get detail element
        TextView balanceTextView = rowView.findViewById(R.id.view_accountBal);

        // Set the account
        Account account = (Account) getItem(position);
        nameTextView.setText(account.getId() + ": " + account.getName());
        typeTextView.setText(db.getAccountTypeName(account.getType()));
        balanceTextView.setText(account.getBalance().toString());

        return rowView;
    }
}
