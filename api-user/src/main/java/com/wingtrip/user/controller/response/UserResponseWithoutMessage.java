package com.wingtrip.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseWithoutMessage {
    private Long id;
    private String name;
    private String lastname;
    private String address;
    private String email;
    private String username;
}
