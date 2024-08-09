package com.wingtrip.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Long userId;

    @NotBlank(message = "The name is required")
    private String name;

    @NotBlank(message = "The lastname is required")
    private String lastname;

    private String address;

    @NotBlank(message = "The email is required")
    @Email(message = "The email must be in a valid format")
    private String email;

    @NotBlank(message = "The username is required")
    private String username;

    @NotBlank(message = "The password is required")
    private String password;
}
