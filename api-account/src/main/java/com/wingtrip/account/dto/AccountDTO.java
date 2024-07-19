package com.wingtrip.account.dto;

import com.wingtrip.account.model.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Long accountId;

    private String document;

    private AccountUserDTO user;

    private Long bookingId;

    public AccountDTO(AccountEntity accountEntity) {
            this.accountId = accountEntity.getAccountId();
            this.document = accountEntity.getDocument();
            this.user = new AccountUserDTO(accountEntity.getUserId());
            this.bookingId = accountEntity.getBookingId();
    }

    public AccountDTO(AccountUserDTO accountUserDTO) {
    }
}
