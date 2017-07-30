package com.bank.bankingapp.account;

import com.bank.bank.Bank;
import com.bank.generics.AccountTypes;

import java.math.BigDecimal;

/**
 * Created by wesle on 2017-07-23.
 */
public class RSA extends Account{

  protected boolean tellerAuthenticated = false;

  /**
   * Constructor for RSA
   *
   * @param id of account
   * @param name of account
   * @param balance of account
   */
  public RSA(int id, String name, BigDecimal balance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.typeInfo = Bank.accountsMap.get(AccountTypes.RSA);
  }
}
