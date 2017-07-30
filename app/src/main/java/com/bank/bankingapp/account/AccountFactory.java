package com.bank.bankingapp.account;

import com.bank.bank.Bank;
import com.bank.generics.AccountTypes;

import java.math.BigDecimal;

public class AccountFactory {

  /**
   * Builds and returns an account based on the given account type and account information
   * 
   * @param accountTypeId The type of account
   * @param accountId The accounts id
   * @param name The name of the account
   * @param balance The balance of the account
   * @return The account built based on given information
   */
  public static Account getAccount(int accountTypeId, int accountId, String name, BigDecimal balance) {

    // If the account type id matches to chequing then build a chequing account
    if (accountTypeId == Bank.accountsMap.get(AccountTypes.CHEQUING).getId()) {
      return new ChequingAccount(accountId, name, balance);

      // If the account type id matches to savings then build a savings account
    } else if (accountTypeId == Bank.accountsMap.get(AccountTypes.SAVING).getId()) {
      return new SavingsAccount(accountId, name, balance);

      // If the account type id matches to TFSA then build a TFSA
    } else if (accountTypeId == Bank.accountsMap.get(AccountTypes.TFSA).getId()) {
      return new TFSA(accountId, name, balance);

      // If the account type id matches to RSA then build a RSA
    } else if (accountTypeId == Bank.accountsMap.get(AccountTypes.RSA).getId()) {
      return new RSA(accountId, name, balance);

      // If the account type id matches to RSA then build a BOA
    } else if (accountTypeId == Bank.accountsMap.get(AccountTypes.BOA).getId()) {
      return new BOA(accountId, name, balance);

      // Otherwise return an exception
    } else {
      return null;
    }
  }

}
