package com.wingtrip.account.dto;

import com.wingtrip.account.model.AccountEntity;
import com.wingtrip.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    //Método para actualizar el usuario
    public void setUserDetails(UserDTO userDTO) {
        this.user = new AccountUserDTO(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getLastname(),
                userDTO.getEmail()
        );
    }

}
