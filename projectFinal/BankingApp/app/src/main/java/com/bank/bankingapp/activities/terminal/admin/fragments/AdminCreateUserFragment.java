package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bank.bankingapp.R;

public class AdminCreateUserFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_admin_create_user, container, false);
    }

    public String getUsername() {
        EditText username = getView().findViewById(R.id.admin_create_name);
        return username.getText().toString();
    }

    public int getAge() {
        EditText age = getView().findViewById(R.id.admin_create_age);
        return Integer.parseInt(age.getText().toString());
    }

    public String getAddress() {
        EditText address = getView().findViewById(R.id.admin_create_address);
        return address.getText().toString();
    }

    public int getType() {
        EditText type = getView().findViewById(R.id.admin_create_type);
        return Integer.parseInt(type.getText().toString());
    }

    public String getPassword() {
        EditText password = getView().findViewById(R.id.admin_create_password);
        return password.getText().toString();
    }

    public void resetFields() {
        EditText username = getView().findViewById(R.id.admin_create_name);
        EditText age = getView().findViewById(R.id.admin_create_age);
        EditText address = getView().findViewById(R.id.admin_create_address);
        EditText type = getView().findViewById(R.id.admin_create_type);
        EditText password = getView().findViewById(R.id.admin_create_password);

        username.setText("");
        age.setText("");
        address.setText("");
        type.setText("");
        password.setText("");
    }
}
