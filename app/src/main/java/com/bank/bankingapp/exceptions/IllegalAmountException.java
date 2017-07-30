package com.bank.bankingapp.exceptions;

/**
 * Thrown if the user attempts to deposit an invalid amount into their account
 * 
 * @author Raya Farhadi
 *
 */
public class IllegalAmountException extends Exception {

  private static final long serialVersionUID = 1L;

  public IllegalAmountException(String e) {
    super(e);
  }

  public IllegalAmountException() {
    super();
  }
}
