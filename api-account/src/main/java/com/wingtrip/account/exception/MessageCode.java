package com.wingtrip.account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum MessageCode {

    ACCOUNT_NOT_FOUND("The account is not found."),
    ACCOUNT_NULL("The account is null."),
    ACCOUNT_NOT_EXIST("The account does not exist."),
    ACCOUNT_NOT_CREATE("The account was not created correctly."),
    ACCOUNT_NOT_DELETED("The account was not deleted."),
    ACCOUNT_DELETE_FAILED("Account deletion failed."),
    USER_NOT_FOUND("The user ID is not found."),
    INVALID_USER_ID("The account with the user id is invalidate."),
    ACCOUNT_BOOKING_NOT_FOUND("The account with the booking id was not found."),
    INVALID_DOCUMENT("The document is invalidate."),
    INVALID_USERNAME("The username is invalidate."),
    DUPLICATE_DOCUMENT("Please enter a valid document, it is a duplicate.");

    final String msg;
}
