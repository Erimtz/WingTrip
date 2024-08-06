package com.wingtrip.account.controller.response;

import com.wingtrip.account.dto.AccountDTO;
import com.wingtrip.account.dto.AccountUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long accountId;
    private String document;
    private AccountUserDTO user;
    private Long bookingId;
    private String message;

    public AccountResponse(AccountDTO accountDTO) {
        this.accountId = accountDTO.getAccountId();
        this.document = accountDTO.getDocument();
        this.user = accountDTO.getUser();
        this.bookingId = accountDTO.getBookingId();
    }
}
