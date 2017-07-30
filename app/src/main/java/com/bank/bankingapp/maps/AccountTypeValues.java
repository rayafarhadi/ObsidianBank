package com.bank.bankingapp.maps;

import java.math.BigDecimal;

public class AccountTypeValues {
  private int id;
  private String name;
  private BigDecimal interest;

  /**
   * Constructor. Initializes the account type id, name, and interest rate.
   * 
   * @param id The account type id
   * @param name The account type name
   * @param interest The account type interest rate
   */
  public AccountTypeValues(int id, String name, BigDecimal interest) {
    this.id = id;
    this.name = name;
    this.interest = interest;
  }

  /**
   * Returns the id
   * 
   * @return The id
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the name
   * 
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the interest rate
   * 
   * @return The interest rate
   */
  public BigDecimal getInterest() {
    return interest;
  }
}
