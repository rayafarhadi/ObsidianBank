package com.bank.bankingapp.account;

import com.bank.bank.Bank;
import com.bank.generics.AccountTypes;

import java.math.BigDecimal;

public class BOA extends Account{



  public BOA(int id, String name, BigDecimal balance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.typeInfo = Bank.accountsMap.get(AccountTypes.BOA);
  }
}
