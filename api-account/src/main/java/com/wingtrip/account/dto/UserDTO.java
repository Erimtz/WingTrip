package com.wingtrip.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
        private Long id;
        private String name;
        private String lastname;
        private String email;


        public UserDTO(Long value) {
                this.id = value;
        }
}