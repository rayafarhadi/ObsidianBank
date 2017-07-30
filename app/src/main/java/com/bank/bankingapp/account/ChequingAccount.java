package com.bank.bankingapp.account;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.AccountTypes;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChequingAccount extends Account implements Serializable {

    private static final long serialVersionUID = 2943743498395307150L;

    /**
     * Constructor for chequing account
     *
     * @param id      of account
     * @param name    of account
     * @param balance of account
     */
    public ChequingAccount(int id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.typeInfo = Bank.accountsMap.get(AccountTypes.CHEQUING);
    }
}
