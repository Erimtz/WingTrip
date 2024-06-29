package com.wingtrip.account.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "accountId")
    private Long accountId;

    @Column(name = "document")
    private String document;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "bookingId")
    private Long bookingId;

}
