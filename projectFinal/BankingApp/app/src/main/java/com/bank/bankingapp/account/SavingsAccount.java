package com.bank.account;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bank.bank.Bank;
import com.bank.generics.AccountTypes;

public class SavingsAccount extends Account implements Serializable{

  private static final long serialVersionUID = 553499615876333440L;

  /**
   * Constructor for SavingsAccount
   * 
   * @param id of account
   * @param name of account
   * @param balance of account
   */
  public SavingsAccount(int id, String name, BigDecimal balance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.typeInfo = Bank.accountsMap.get(AccountTypes.SAVING);
  }
}
