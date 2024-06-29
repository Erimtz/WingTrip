package com.wingtrip.account.controller.request;

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

    @NotBlank(message = "The user is required")
    private Long userId;

    private Long bookingId;
}
