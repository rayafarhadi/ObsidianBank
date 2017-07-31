package com.bank.bankingapp.account;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.AccountTypes;

import java.io.Serializable;
import java.math.BigDecimal;

public class TFSA extends Account implements Serializable {

    private static final long serialVersionUID = -1352980863389787562L;

    /**
     * Constructor for TSFA
     *
     * @param id      of account
     * @param name    of account
     * @param balance of account
     */
    public TFSA(int id, String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
        this.typeInfo = Bank.accountsMap.get(AccountTypes.TFSA);
        this.id = typeInfo.getId();
        this.interestRate = typeInfo.getInterest();
    }
}
