package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bank.bankingapp.R;
import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.messages.Message;

import java.util.List;


public class AdminMessagesAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Message> mDataSource;

    /**
     * Initializes the admin message and sets layout on screen
     * @param context
     * @param items List of messages
     */
    public AdminMessagesAdapter(Context context, List<Message> items) {
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
        View rowView = mInflater.inflate(R.layout.view_admin_message_item, parent, false);

        // Get Name element
        TextView messageView = rowView.findViewById(R.id.view_message);

        // Set the account
        final Message message = (Message) getItem(position);
        messageView.setText(message.getMessage());
        if (message.isViewed()){
            messageView.setTextColor(Color.parseColor("#616161"));
        }else {
            messageView.setTextColor(Color.parseColor("#4CAF50"));
        }

        // Change set to read on click
        messageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.viewMessage(mContext);
            }
        });

        return rowView;
    }
}

