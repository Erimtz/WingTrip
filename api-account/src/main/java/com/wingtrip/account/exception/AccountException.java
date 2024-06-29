package com.wingtrip.account.exception;

public class AccountException extends Exception {

    public AccountException(MessageCode exp) {
        super(exp.getMsg());
    }

}
