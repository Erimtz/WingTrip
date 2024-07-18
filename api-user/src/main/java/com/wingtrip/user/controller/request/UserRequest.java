package com.wingtrip.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min = 1)
    private Long userId;

    @Size(min = 2, max = 15)
    private String name;

    @Size(min = 2, max = 15)
    private  String lastname;

    @Size(min = 10, max = 25)
    private String address;

    @NotBlank(message = "Email is required")
    @Email(message = "The email must be in a valid format")
    private String email;

    @NotBlank(message = "The username is required")
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 15)
    private String password;
}
