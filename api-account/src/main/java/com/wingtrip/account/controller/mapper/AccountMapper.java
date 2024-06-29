package com.wingtrip.account.controller.mapper;

import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.controller.response.AccountResponse;
import com.wingtrip.account.dto.AccountDTO;
import com.wingtrip.account.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.Optional;


@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO dtoToRequest(AccountRequest request);

    AccountResponse toResponse(AccountDTO accountResponse);

    default AccountResponse toResponse(Optional<AccountDTO> accountDTO) {
        return accountDTO.map(this::toResponse).orElse(null);
    }

    default UserDTO map(Long value) {
        return new UserDTO(value);
    }

}
