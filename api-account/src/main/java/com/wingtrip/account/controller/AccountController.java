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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        AccountDTO dtoToRequest = accountMapper.dtoToRequest(request);

        AccountDTO accountDTO1 = accountServiceImpl.createAccount(dtoToRequest);

        AccountResponse accountResponse = accountMapper.toResponse(accountDTO1);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Search account by user ID")
    @GetMapping("/get/user/{id}")
    public ResponseEntity<AccountResponse> findByUserId(@PathVariable Long userId) throws AccountException {
        Optional<AccountDTO> accountDTO = accountServiceImpl.findByUserId(userId);

        AccountResponse accountResponse = accountMapper.toResponse(accountDTO);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Search account by booking ID")
    @GetMapping("/get/booking/{id}")
    public ResponseEntity<AccountResponse> findByBookingId(@PathVariable Long bookingId) throws AccountException {
        Optional<AccountDTO> accountDTO = accountServiceImpl.findByBookingId(bookingId);

        AccountResponse accountResponse = accountMapper.toResponse(accountDTO);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Update account by user ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long userId, @RequestBody AccountRequest request) throws AccountException {
        AccountDTO accountDTO1 = accountServiceImpl.updateAccount(userId, request);

        AccountResponse accountResponse = accountMapper.toResponse(accountDTO1);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Delete account by user ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteByUserId(@PathVariable Long userId) throws AccountException {
        boolean isDeleted = accountServiceImpl.deleteByUserId(userId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else throw  new AccountException(MessageCode.ACCOUNT_BOOKING_NOT_FOUND);
    }
}

