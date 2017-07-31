package com.bank.bankingapp.account;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.AccountTypes;

import java.io.Serializable;
import java.math.BigDecimal;

public class SavingsAccount extends Account implements Serializable {

    private static final long serialVersionUID = 553499615876333442L;

    /**
     * Constructor for SavingsAccount
     *
     * @param id      of account
     * @param name    of account
     * @param balance of account
     */
    public SavingsAccount(int id, String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
        this.typeInfo = Bank.accountsMap.get(AccountTypes.SAVING);
        this.id = typeInfo.getId();
        this.interestRate = typeInfo.getInterest();
    }
}
