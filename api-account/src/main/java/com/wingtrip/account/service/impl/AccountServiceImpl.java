package com.wingtrip.account.service.impl;

import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.dto.AccountDTO;
import com.wingtrip.account.dto.AccountUserDTO;
import com.wingtrip.account.exception.AccountException;
import com.wingtrip.account.exception.MessageCode;
import com.wingtrip.account.model.AccountEntity;
import com.wingtrip.account.repository.AccountRepository;
import com.wingtrip.account.service.AccountService;
import com.wingtrip.user.client.UserFeignClient;
import com.wingtrip.user.dto.UserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final UserFeignClient userFeignClient;

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) throws AccountException {
        try {
            AccountEntity accountEntity = AccountEntity.builder()
                    .document(accountDTO.getDocument())
                    .userId(accountDTO.getUser().getId())
                    .bookingId(accountDTO.getBookingId())
                    .build();
            AccountEntity saveEntity = accountRepository.save(accountEntity);
            // Llamar al microservicio de `api-user` para obtener los detalles del usuario completo
            // utilizando Feign Client o RestTemplate y asignar los detalles del usuario completo al DTO.

            return new AccountDTO(saveEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AccountException(MessageCode.ACCOUNT_NOT_CREATE);
        }
    }

    @Override
    public AccountDTO findByUserId(Long userId) throws AccountException {
        if (userId == null) {
            throw new AccountException(MessageCode.ACCOUNT_NULL);
        }

        Optional<AccountEntity> entityOptional = accountRepository.findById(userId);
        if (entityOptional.isPresent() ) {
            return new AccountDTO(entityOptional.get());
        } else {
            throw new AccountException(MessageCode.ACCOUNT_NOT_EXIST);
        }
    }

    @Override
    public AccountDTO findByBookingId(Long bookingId) throws AccountException {
        if (bookingId == null) {
            throw new AccountException(MessageCode.ACCOUNT_NULL);
        }

        Optional<AccountEntity> optionalAccount = accountRepository.findByBookingId(bookingId);
        if (optionalAccount.isEmpty()) {
            throw new AccountException(MessageCode.ACCOUNT_NOT_FOUND);
        } else {
            AccountEntity accountEntity = optionalAccount.get();
            return new AccountDTO(accountEntity);
        }
    }

    @Override
    public AccountDTO updateAccount(Long userId, AccountRequest accountRequest) throws AccountException {
        if (userId == null && accountRequest == null) {
            throw new AccountException(MessageCode.ACCOUNT_NULL);
        }

        Optional<AccountEntity> accountEntity = accountRepository.findById(userId);
        if (accountEntity.isEmpty()) {
            throw new AccountException(MessageCode.ACCOUNT_NOT_FOUND);
        }

        AccountEntity accountEntity1 = accountEntity.get();
        accountEntity1.setDocument(accountRequest.getDocument());
        accountEntity1.setUserId(accountRequest.getUser().getId());
        accountEntity1.setBookingId(accountRequest.getBookingId());

        AccountEntity updateEntity = accountRepository.save(accountEntity1);
        return new AccountDTO(updateEntity);
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

    public AccountDTO getAccountByUserId(String username) throws AccountException {
        try {
            if (username == null || username.isEmpty()) {
                throw new AccountException(MessageCode.INVALID_USERNAME);
            }

            UserDTO userDTO = userFeignClient.getUserByUsername(username);
            AccountEntity accountEntity = accountRepository.findById(userDTO.getId())
                    .orElseThrow(() -> new AccountException(MessageCode.ACCOUNT_NOT_FOUND));

            return new AccountDTO(accountEntity);

        } catch (FeignException e) {
            throw new AccountException(MessageCode.USER_NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            throw new AccountException(MessageCode.DUPLICATE_DOCUMENT);
        }
    }

    public AccountDTO getBookingIdByUserId(Long userId) throws AccountException {
        if (userId == null) {
            throw new AccountException(MessageCode.INVALID_USER_ID);
        }

        try {
            Long bookingId = userFeignClient.getBookingId(userId);
            AccountEntity accountEntity = accountRepository.findById(userId)
                    .orElseThrow(() -> new AccountException(MessageCode.ACCOUNT_NOT_FOUND));

            accountEntity.setBookingId(bookingId);
            accountRepository.save(accountEntity);


            UserDTO userDTO = userFeignClient.getUserById(userId);
            AccountUserDTO accountUserDTO = new AccountUserDTO(userDTO.getId());
            return new AccountDTO(accountUserDTO);

        } catch (FeignException e) {
            throw new AccountException(MessageCode.ACCOUNT_BOOKING_NOT_FOUND);
        }
    }
}
