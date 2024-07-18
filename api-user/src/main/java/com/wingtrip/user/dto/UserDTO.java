package com.wingtrip.user.dto;


import com.wingtrip.user.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private  String lastname;

    private String address;

    private String email;

    private String username;

    private String password;

    public UserDTO(UserEntity userEntity) {
    }

    public UserDTO(Optional<UserEntity> optionalUserId) {
    }


    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(id);
        userEntity.setName(name);
        userEntity.setLastname(lastname);
        userEntity.setEmail(email);
        userEntity.setAddress(address);
        userEntity.setUsername(username);
        userEntity.setPassword(password);

        return userEntity;
    }

}
