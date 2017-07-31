package com.bank.bankingapp.account;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.AccountTypes;

import java.io.Serializable;
import java.math.BigDecimal;

public class BOA extends Account implements Serializable {

    private static final long serialVersionUID = 8624114715497524875L;

    public BOA(int id, String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
        this.typeInfo = Bank.accountsMap.get(AccountTypes.BOA);
        this.id = typeInfo.getId();
        this.interestRate = typeInfo.getInterest();
    }
}
