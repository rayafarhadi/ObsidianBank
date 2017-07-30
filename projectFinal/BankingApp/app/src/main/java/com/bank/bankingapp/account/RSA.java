package com.bank.account;

import com.bank.bank.Bank;
import com.bank.generics.AccountTypes;
import java.io.Serializable;
import java.math.BigDecimal;

public class RSA extends Account implements Serializable{

  private static final long serialVersionUID = 4335923625922600246L;
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
