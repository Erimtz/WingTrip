package com.wingtrip.account.controller.request;

import com.wingtrip.account.dto.AccountUserDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    private Long accountId;

    @NotBlank(message = "The document is required")
    private String document;

    @Valid
    private AccountUserDTO user;

    private Long bookingId;
}
