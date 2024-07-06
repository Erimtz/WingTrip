package com.wingtrip.account.controller.mapper;

import com.wingtrip.account.controller.request.AccountRequest;
import com.wingtrip.account.controller.response.AccountResponse;
import com.wingtrip.account.dto.AccountDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO dtoToRequest(AccountRequest request);

    AccountResponse toResponse(AccountDTO accountDTO);

}
