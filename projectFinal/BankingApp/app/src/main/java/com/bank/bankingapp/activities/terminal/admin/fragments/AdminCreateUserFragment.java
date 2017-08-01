package com.bank.bankingapp.activities.terminal.admin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bank.bankingapp.R;
import com.bank.bankingapp.bank.Bank;


public class AdminCreateUserFragment extends Fragment {

    int spinnerPos;

    /**
     * Initializes the create user activity and sets layout of the screen
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_admin_create_user, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.view_admin_balance_item, new String[]{"Admin", "Teller", "Customer"});

        final Spinner type = (Spinner) view.findViewById(R.id.create_user_type_spinner);
        type.setPrompt("-- Type --");

        type.setAdapter(adapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPos(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setPos(int pos) {
        spinnerPos = pos;
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
        Spinner type = getView().findViewById(R.id.create_user_type_spinner);

        for (String role : Bank.rolesMap.getNames()) {
            if (role.equalsIgnoreCase(type.getAdapter().getItem(spinnerPos).toString())) {
                return Bank.rolesMap.getNames().indexOf(role) + 1;
            }
        }

        return 0;
    }

    public String getPassword() {
        EditText password = getView().findViewById(R.id.admin_create_password);
        return password.getText().toString();
    }

    /**
     * when called resets the fields of the input text boxes
     */
    public void resetFields() {
        EditText username = getView().findViewById(R.id.admin_create_name);
        EditText age = getView().findViewById(R.id.admin_create_age);
        EditText address = getView().findViewById(R.id.admin_create_address);
        Spinner type = getView().findViewById(R.id.create_user_type_spinner);
        EditText password = getView().findViewById(R.id.admin_create_password);

        username.setText("");
        age.setText("");
        address.setText("");
        type.setPrompt("-- Type --");
        password.setText("");
    }
}
