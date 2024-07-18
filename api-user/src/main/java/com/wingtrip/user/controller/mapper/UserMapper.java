package com.wingtrip.user.controller.mapper;

import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.controller.response.UserResponse;
import com.wingtrip.user.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toRequest(UserRequest request);

    UserResponse toResponse(UserDTO userDTO);
}
