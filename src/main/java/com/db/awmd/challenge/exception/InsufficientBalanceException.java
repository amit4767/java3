package com.db.awmd.challenge.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String errormessage){ super(errormessage);}
}
