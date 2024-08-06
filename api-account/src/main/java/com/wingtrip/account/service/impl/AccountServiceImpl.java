package com.wingtrip.account.service.impl;

import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.dto.AccountDTO;
import com.wingtrip.account.dto.AccountUserDTO;
import com.wingtrip.account.exception.AccountException;
import com.wingtrip.account.exception.MessageCode;
import com.wingtrip.account.model.AccountEntity;
import com.wingtrip.account.repository.AccountRepository;
import com.wingtrip.account.service.AccountService;
import com.wingtrip.account.client.UserFeignClient;
import com.wingtrip.user.dto.UserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final UserFeignClient userFeignClient;

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountEntity -> {
                    AccountDTO accountDTO = new AccountDTO(accountEntity);

                    //Llamar al microservicio api-user para buscar el usuario
                    UserDTO userDTO = userFeignClient.getUserById(accountDTO.getUser().getId());

                    //Mapear el UserDTO a AccountDTO
                    AccountUserDTO accountUserDTO = new AccountUserDTO(
                            userDTO.getId(),
                            userDTO.getName(),
                            userDTO.getLastname(),
                            userDTO.getEmail()
                    );

                    accountDTO.setUser(accountUserDTO);
                    return accountDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) throws AccountException {
        try {
            // Llamar al microservicio de api-user`para obtener los detalles completos del usuario
            UserDTO userDTO = userFeignClient.getUserById(accountDTO.getUser().getId());

            // Mapear el UserDTO a AccountUserDTO
            AccountUserDTO accountUserDTO = convertAccountUserDTO(userDTO);

            AccountEntity accountEntity = AccountEntity.builder()
                    .document(accountDTO.getDocument())
                    .userId(accountUserDTO.getId())
                    .bookingId(accountDTO.getBookingId())
                    .build();

            AccountEntity saveEntity = accountRepository.save(accountEntity);

            // Crear el AccountDTO con el AccountUserDTO completo
            AccountDTO createAccountDTO = new AccountDTO(saveEntity);
            createAccountDTO.setUser(accountUserDTO);

            return createAccountDTO;
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
        log.info("Searching for account with userId: {}", userId);

        Optional<AccountEntity> entityOptional = accountRepository.findByUserId(userId);
        if (entityOptional.isPresent() ) {
            log.info("Account found: {}", entityOptional.get());

            UserDTO userDTO = userFeignClient.getUserById(userId);
            log.info("User fetched from Feign Client: {}", userDTO);

            AccountUserDTO accountUserDTO = convertAccountUserDTO(userDTO);
            return new AccountDTO(entityOptional.get(), accountUserDTO);
        } else {
            log.error("Account not found for userId: {}", userId);
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

            UserDTO userDTO = userFeignClient.getBookingId(bookingId);

            AccountUserDTO accountUserDTO = convertAccountUserDTO(userDTO);
            return new AccountDTO(accountEntity, accountUserDTO);
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

        // Llamada al Feign Client para obtener el usuario y actualizarlo
        UserDTO userDTO = userFeignClient.getUserById(accountRequest.getUser().getId());

        if (accountRequest.getUser().getName() != null) {
            userDTO.setName(accountRequest.getUser().getName());
        }
        if (accountRequest.getUser().getLastname() != null) {
            userDTO.setLastname(accountRequest.getUser().getLastname());
        }
        if (accountRequest.getUser().getEmail() != null) {
            userDTO.setEmail(accountRequest.getUser().getEmail());
        }

        //Llamada al Feign para actualizar el usuario
        userFeignClient.updateUser(userDTO.getId(), userDTO);

        AccountEntity updateEntity = accountRepository.save(accountEntity1);

        UserDTO updateUserDTO = userFeignClient.getUserById(accountRequest.getUser().getId());
        AccountUserDTO accountUserDTO = convertAccountUserDTO(updateUserDTO);

        return new AccountDTO(updateEntity, accountUserDTO);
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
            Long bookingId = userFeignClient.getBookingId(userId).getId();
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

    private AccountUserDTO convertAccountUserDTO(UserDTO userDTO) {
        return new AccountUserDTO(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getLastname(),
                userDTO.getEmail()
        );
    }
}
