package com.wingtrip.account.dto;

import com.wingtrip.account.model.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Long accountId;

    private String document;

    private UserDTO user;

    private Long bookingId;

    public AccountDTO(Optional<AccountEntity> accountEntity) {
        if (accountEntity.isPresent()) {
            this.accountId = accountEntity.get().getAccountId();
            this.document = accountEntity.get().getDocument();
            this.user = new UserDTO(accountEntity.get().getUserId());
            this.bookingId = accountEntity.get().getBookingId();
        }
    }

    public AccountDTO(Long accountId, String document, Long user) {
        this.accountId = accountId;
        this.document = document;
        this.user = new UserDTO(user);
    }
}
