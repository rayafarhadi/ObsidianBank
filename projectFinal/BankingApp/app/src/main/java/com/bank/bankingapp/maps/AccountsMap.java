package com.bank.bankingapp.maps;

import android.content.Context;

import com.bank.bankingapp.database.DatabaseHelper;
import com.bank.bankingapp.generics.AccountTypes;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

public class AccountsMap extends EnumMap<AccountTypes, AccountTypeValues> {

    /**
     *
     */
    private static final long serialVersionUID = 4426185117622692768L;

    /**
     * Constructor. Initiliazes the AccountsMap and updates it to the current database.
     *
     * @throws SQLException When connection to the database is lost
     */
    public AccountsMap(Context context) {
        super(AccountTypes.class);
        update(context);
    }

    /**
     * Returns a list of all account type ids
     *
     * @return A list of all account type ids
     */
    public List<Integer> getIds() {
        Collection<AccountTypeValues> values = values();
        List<Integer> ids = new ArrayList<Integer>();
        for (AccountTypeValues value : values) {
            ids.add(value.getId());
        }

        return ids;
    }

    /**
     * Returns a list of all account names
     *
     * @return A list of all account names
     */
    public List<String> getNames() {
        Collection<AccountTypeValues> values = values();
        List<String> names = new ArrayList<String>();
        for (AccountTypeValues value : values) {
            names.add(value.getName());
        }
        return names;
    }

    /**
     * Returns a list of all account interest rates
     *
     * @return A list of all account interest rates
     */
    public List<BigDecimal> getInterestRates() {
        Collection<AccountTypeValues> values = values();
        List<BigDecimal> rates = new ArrayList<BigDecimal>();
        for (AccountTypeValues value : values) {
            rates.add(value.getInterest());
        }
        return rates;
    }

    /**
     * Goes through the database and pairs all AccountTypes with their corresponding information in
     * the database
     *
     * @throws SQLException When connection with the database is lost
     */
    public void update(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        int id = 1;

        for (AccountTypes accountType : AccountTypes.values()) {
            put(accountType, new AccountTypeValues(id, db.getAccountTypeName(id),
                    db.getInterestRate(id)));
            id++;
        }
    }
}
