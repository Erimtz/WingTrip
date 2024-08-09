package com.wingtrip.user.exception;

public class UserException extends Exception {

    public UserException(MessageCode exp) {
        super(exp.getMsg());
    }
}
