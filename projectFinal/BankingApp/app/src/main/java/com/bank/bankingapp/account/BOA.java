package com.bank.account;

import com.bank.bank.Bank;
import com.bank.generics.AccountTypes;
import java.io.Serializable;
import java.math.BigDecimal;

public class BOA extends Account implements Serializable{

  private static final long serialVersionUID = 8624114715497524873L;

  public BOA(int id, String name, BigDecimal balance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.typeInfo = Bank.accountsMap.get(AccountTypes.BOA);
  }
}
