package com.wingtrip.account.service.impl;

import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.dto.AccountDTO;
import com.wingtrip.account.exception.AccountException;
import com.wingtrip.account.exception.MessageCode;
import com.wingtrip.account.model.AccountEntity;
import com.wingtrip.account.repository.AccountRepository;
import com.wingtrip.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::toConvert)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) throws AccountException {
        try {
            AccountEntity accountEntity = AccountEntity.builder()
                    .accountId(accountDTO.getAccountId())
                    .document(accountDTO.getDocument())
                    .userId(accountDTO.getUser().getId())
                    .build();
            accountRepository.save(accountEntity);
            return toConvert(accountEntity);
        } catch (Exception e) {
            throw new AccountException(MessageCode.ACCOUNT_NOT_CREATE);
        }
    }

    @Override
    public Optional<AccountDTO> findByUserId(Long userId) throws AccountException {
        if (userId == null) {
            throw new AccountException(MessageCode.ACCOUNT_NULL);
        }

        Optional<AccountEntity> entityOptional = accountRepository.findById(userId);
        if (entityOptional.isPresent() ) {
            AccountEntity accountEntity = entityOptional.get();
            accountEntity.setUserId(userId);
            accountRepository.save(accountEntity);
        } else {
            throw new AccountException(MessageCode.ACCOUNT_NOT_EXIST);
        }
        return Optional.of(new AccountDTO(entityOptional));
    }

    @Override
    public Optional<AccountDTO> findByBookingId(Long bookingId) throws AccountException {
        if (bookingId == null) {
            throw new AccountException(MessageCode.ACCOUNT_NULL);
        }

        Optional<AccountEntity> optionalAccount = accountRepository.findById(bookingId);
        if (optionalAccount.isPresent()) {
            AccountEntity accountEntity = optionalAccount.get();
            accountEntity.setBookingId(bookingId);
            accountRepository.save(accountEntity);
        } else {
            throw new AccountException(MessageCode.ACCOUNT_NOT_FOUND);
        }

        return Optional.of(new AccountDTO(optionalAccount));
    }

    @Override
    public AccountDTO updateAccount(Long userId, AccountRequest accountRequest) throws AccountException {
        if (userId == null && accountRequest == null) {
            throw new AccountException(MessageCode.ACCOUNT_NULL);
        }

        assert userId != null;
        Optional<AccountEntity> accountEntity = accountRepository.findById(userId);
        if (accountEntity.isEmpty()) {
            throw new AccountException(MessageCode.ACCOUNT_NOT_FOUND);
        }

        AccountEntity accountEntity1 = accountEntity.get();
        accountEntity1.setAccountId(accountEntity1.getAccountId());
        accountEntity1.setDocument(accountEntity1.getDocument());
        accountEntity1.setUserId(accountEntity1.getUserId());

        accountEntity = Optional.of(accountRepository.save(accountEntity1));

        return new AccountDTO(accountEntity);
    }

    @Override
    public boolean deleteByUserId(Long userId) throws AccountException {
        if (!accountRepository.existsById(userId)) {
            throw new AccountException(MessageCode.ACCOUNT_NOT_FOUND);
        }

        try {
            accountRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            throw new AccountException(MessageCode.ACCOUNT_DELETE_FAILED);
        }
    }

    @Override
    public AccountDTO toConvert(AccountEntity accountEntity) {
        return new AccountDTO(
                accountEntity.getAccountId(),
                accountEntity.getDocument(),
                accountEntity.getUserId()
        );
    }
}
