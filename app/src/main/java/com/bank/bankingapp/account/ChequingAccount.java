package com.bank.bankingapp.account;

import com.bank.bank.Bank;
import com.bank.generics.AccountTypes;

import java.math.BigDecimal;

public class ChequingAccount extends Account {

  /**
   * Constructor for chequing account
   * 
   * @param id of account
   * @param name of account
   * @param balance of account
   */
  public ChequingAccount(int id, String name, BigDecimal balance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.typeInfo = Bank.accountsMap.get(AccountTypes.CHEQUING);
  }
}
