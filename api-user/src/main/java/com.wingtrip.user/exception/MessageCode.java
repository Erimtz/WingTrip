package com.wingtrip.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageCode {

    USER_NOT_FOUND("The user not found."),
    USER_ID_NOT_FOUND("The id for user not found."),
    USER_NOT_CREATE("The user was not created correctly."),
    USER_NOT_EXIST("The user does not exist"),
    EMAIL_USER_NOT_FOUND("The email not found."),
    EMAIL_CREATE_BEFORE("The email has been create before."),
    USERNAME_NOT_FOUND("The username not found."),
    USERNAME_CREATE_BEFORE("The username has been create before."),
    USERNAME_NULL("The user is null."),
    //SEAT_NOT_LIMIT("Limit exceeded."),
    USER_NOT_TOTAL_AMOUNT_EQUALS("Verify details amounts."),
    USER_DELETE_FAILED("The delete is not processed correctly.");

    final String msg;

}
