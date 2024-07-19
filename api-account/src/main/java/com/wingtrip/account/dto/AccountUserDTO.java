package com.wingtrip.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUserDTO {
        private Long id;
        private String name;
        private String lastname;
        private String email;


        public AccountUserDTO(Long value) {
                this.id = value;
        }
}