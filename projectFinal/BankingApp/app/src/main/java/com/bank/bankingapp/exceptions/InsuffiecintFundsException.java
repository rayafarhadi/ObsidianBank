package com.bank.bankingapp.exceptions;

/**
 * Exception thrown when a user attempts to withdraw more money then is available in their account
 * 
 * @author Raya Farhadi
 *
 */
public class InsuffiecintFundsException extends Exception {

  private static final long serialVersionUID = 1L;

  public InsuffiecintFundsException(String e) {
    super(e);
  }

  public InsuffiecintFundsException() {
    super();
  }

}
