package com.wingtrip.account.service;

import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.dto.AccountDTO;
import com.wingtrip.account.exception.AccountException;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAllAccounts();
    AccountDTO createAccount(AccountDTO accountDTO) throws AccountException;
    AccountDTO findByUserId(Long userId) throws AccountException;
    AccountDTO findByBookingId(Long bookingId) throws AccountException;
    AccountDTO updateAccount(Long userId, AccountRequest accountRequest) throws AccountException;
    boolean deleteByUserId(Long userId) throws AccountException;
}
