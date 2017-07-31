package com.bank.bankingapp.account;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.AccountTypes;

import java.io.Serializable;
import java.math.BigDecimal;

public class RSA extends Account implements Serializable {

    private static final long serialVersionUID = 4335923625922600248L;
    protected boolean tellerAuthenticated = false;

    /**
     * Constructor for RSA
     *
     * @param id      of account
     * @param name    of account
     * @param balance of account
     */
    public RSA(int id, String name, BigDecimal balance) {
        super();
        this.name = name;
        this.balance = balance;
        this.typeInfo = Bank.accountsMap.get(AccountTypes.RSA);
        this.id = typeInfo.getId();
        this.interestRate = typeInfo.getInterest();
    }
}
