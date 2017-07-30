package com.bank.bankingapp.account;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bank.bankingapp.bank.Bank;
import com.bank.bankingapp.generics.AccountTypes;

public class TFSA extends Account implements Serializable{

  private static final long serialVersionUID = -1352980863389787560L;

  /**
   * Constructor for TSFA
   * 
   * @param id of account
   * @param name of account
   * @param balance of account
   */
  public TFSA(int id, String name, BigDecimal balance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.typeInfo = Bank.accountsMap.get(AccountTypes.TFSA);
  }
}
