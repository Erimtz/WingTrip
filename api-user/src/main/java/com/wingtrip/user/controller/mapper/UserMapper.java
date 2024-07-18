package com.wingtrip.user.controller.mapper;

import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.controller.response.UserResponse;
import com.wingtrip.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    @Mapping(source = "userId", target = "id")
    UserDTO toUser(UserRequest request);

    UserResponse toResponse(UserDTO toRequest);

    UserResponse toResponse(Optional<UserDTO> toRequest);
}
