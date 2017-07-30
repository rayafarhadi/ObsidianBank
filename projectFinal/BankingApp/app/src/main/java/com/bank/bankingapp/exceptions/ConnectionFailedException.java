package com.bank.bankingapp.exceptions;

/**
 * Thrown if the connection to the database fails
 *
 * @author Raya Farhadi
 */
public class ConnectionFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConnectionFailedException(String e) {
        super(e);
    }

    public ConnectionFailedException() {
        super();
    }

}
