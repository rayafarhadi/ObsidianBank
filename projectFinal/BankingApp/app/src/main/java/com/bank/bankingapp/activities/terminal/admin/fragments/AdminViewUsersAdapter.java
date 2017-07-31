package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.user.User;

import java.util.List;

public class AdminViewUsersAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<User> mDataSource;

    public AdminViewUsersAdapter(Context context, List<User> items) {
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
        DatabaseHelper db = new DatabaseHelper(mContext);
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.view_admin_users_list_item, parent, false);

        // Get Name element
        TextView nameTextView = rowView.findViewById(R.id.view_user_name);

        // Get Type element
        TextView typeTextView = rowView.findViewById(R.id.view_user_type);

        // Get Age/Address element
        TextView ageAddressTextView = rowView.findViewById(R.id.view_age_address);

        // Set the account
        User user = (User) getItem(position);
        nameTextView.setText(user.getName());
        typeTextView.setText(db.getRole(user.getRoleId()));
        ageAddressTextView.setText(user.getAge() + ", " + user.getAddress());

        return rowView;
    }
}

