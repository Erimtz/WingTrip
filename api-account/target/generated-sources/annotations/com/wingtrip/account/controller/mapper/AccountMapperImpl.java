package com.wingtrip.account.controller.mapper;

import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.controller.response.AccountResponse;
import com.wingtrip.account.dto.AccountDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T16:04:03-0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDTO dtoToRequest(AccountRequest request) {
        if ( request == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountId( request.getAccountId() );
        accountDTO.setDocument( request.getDocument() );
        accountDTO.setUser( request.getUser() );
        accountDTO.setBookingId( request.getBookingId() );

        return accountDTO;
    }

    @Override
    public AccountResponse toResponse(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setAccountId( accountDTO.getAccountId() );
        accountResponse.setDocument( accountDTO.getDocument() );
        accountResponse.setUser( accountDTO.getUser() );
        accountResponse.setBookingId( accountDTO.getBookingId() );

        return accountResponse;
    }
}
