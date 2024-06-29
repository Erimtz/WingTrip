package com.wingtrip.account.service;

import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.dto.AccountDTO;
import com.wingtrip.account.exception.AccountException;
import com.wingtrip.account.model.AccountEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<AccountDTO> getAllAccounts();
    AccountDTO createAccount(AccountDTO accountDTO) throws AccountException;
    Optional<AccountDTO> findByUserId(Long userId) throws AccountException;
    Optional<AccountDTO> findByBookingId(Long bookingId) throws AccountException;
    AccountDTO updateAccount(Long userId, AccountRequest accountRequest) throws AccountException;
    boolean deleteByUserId(Long userId) throws AccountException;
    AccountDTO toConvert(AccountEntity accountEntity);
}
