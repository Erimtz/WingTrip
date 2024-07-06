package com.wingtrip.account.controller.response;

import com.wingtrip.account.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long accountId;
    private String document;
    private UserDTO user;
    private Long bookingId;
    private String message;

}
