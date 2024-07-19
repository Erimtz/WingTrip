package com.wingtrip.account.controller;

import com.wingtrip.account.controller.mapper.AccountMapper;
import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.controller.response.AccountResponse;
import com.wingtrip.account.dto.AccountDTO;
import com.wingtrip.account.exception.AccountException;
import com.wingtrip.account.exception.MessageCode;
import com.wingtrip.account.service.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Tag(name = "Account API", description = "API for managing accounts")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController  {

    private final AccountServiceImpl accountServiceImpl;

    private final AccountMapper accountMapper;

    @Operation(summary = "Get all the accounts")
    @GetMapping("/get-all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountServiceImpl.getAllAccounts());
    }

    @Operation(summary = "Create new account")
    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) throws AccountException {
        log.info("Received request to create account with data: {}", request);

        AccountDTO dtoToRequest = accountMapper.dtoToRequest(request);
        AccountDTO createdAccount = accountServiceImpl.createAccount(dtoToRequest);
        AccountResponse accountResponse = accountMapper.toResponse(createdAccount);
        accountResponse.setMessage("Account created successfully");

        log.info("Created account successfully with data: {}", accountResponse);
        return ResponseEntity.ok(accountResponse);

    }

    @Operation(summary = "Search account by user ID")
    @GetMapping("/get/user/{id}")
    public ResponseEntity<AccountResponse> findByUserId(@PathVariable Long id) throws AccountException {
        AccountDTO accountDTO = accountServiceImpl.findByUserId(id);
        AccountResponse accountResponse = accountMapper.toResponse(accountDTO);
        accountResponse.setMessage("Find by user ID successfully with ID: " + id);

        log.info("Find by user ID successfully with data: {}", accountResponse);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Search account by booking ID")
    @GetMapping("/get/booking/{id}")
    public ResponseEntity<AccountResponse> findByBookingId(@PathVariable Long id) throws AccountException {
        AccountDTO accountDTO = accountServiceImpl.findByBookingId(id);
        AccountResponse accountResponse = accountMapper.toResponse(accountDTO);
        accountResponse.setMessage("Find by booking ID successfully with ID: " + id);

        log.info("Find by booking ID successfully with data: {}", accountResponse);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Update account by user ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id, @RequestBody AccountRequest request) throws AccountException {
        AccountDTO updateAccount = accountServiceImpl.updateAccount(id, request);
        AccountResponse accountResponse = accountMapper.toResponse(updateAccount);
        accountResponse.setMessage("Update account successfully with ID: " + id);

        log.info("Update account successfully with data: {}", accountResponse);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Delete account by user ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteByUserId(@PathVariable Long id) throws AccountException {
        boolean isDeleted = accountServiceImpl.deleteByUserId(id);

        if (isDeleted) {
            log.info("Delete by user ID successfully with data: {}", id);
            return ResponseEntity.noContent().build();
        } else throw  new AccountException(MessageCode.ACCOUNT_BOOKING_NOT_FOUND);
    }

    @Operation(summary = "Get account by user ID")
    @GetMapping("/get-account-username/{username}")
    public ResponseEntity<AccountResponse> getAccountByUserId(@Valid @PathVariable String username) throws AccountException {
        AccountDTO accountDTO = accountServiceImpl.getAccountByUserId(username);
        AccountResponse accountResponse = accountMapper.toResponse(accountDTO);
        accountResponse.setMessage("Find account with user feign with username: " + username);

        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Get booking by user ID")
    @GetMapping("/get-booking-id/{userId}")
    public ResponseEntity<AccountResponse> bookingByUserId(@PathVariable Long id) throws AccountException {
        AccountDTO accountDTO = accountServiceImpl.getBookingIdByUserId(id);
        AccountResponse accountResponse = accountMapper.toResponse(accountDTO);
        accountResponse.setMessage("Find booking with user feign byID: " + id);

        return ResponseEntity.ok(accountResponse);
    }
}

