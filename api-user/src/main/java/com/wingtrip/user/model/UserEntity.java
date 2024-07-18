package com.wingtrip.user.model;

import jakarta.persistence.*;
import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Column(name = "userId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private  String lastname;

    @Column(name = "address")
    private String address;

    @Column(unique = true)
    @NotBlank
    @Size(max = 80)
    private String email;

    @Column(unique = true)
    @NotBlank
    @Size(max = 30)
    private String username;

    @Column(name = "password")
    private String password;
}
