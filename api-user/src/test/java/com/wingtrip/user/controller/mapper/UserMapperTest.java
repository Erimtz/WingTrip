package com.wingtrip.user.controller.mapper;

import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.controller.response.UserResponse;
import com.wingtrip.user.controller.response.UserResponseWithoutMessage;
import com.wingtrip.user.dto.UserDTO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private EasyRandom easyRandom;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        easyRandom = new EasyRandom();
    }
     
    
    @Test
    void toDTO() {
        UserRequest userRequest = easyRandom.nextObject(UserRequest.class);

        UserDTO expectedDTO = userMapper.toDTO(userRequest);

        assertThat(expectedDTO).usingRecursiveComparison().ignoringAllOverriddenEquals()
                .ignoringFields("id","userId")
                .isEqualTo(userRequest);

        assertThat(expectedDTO.getId()).isEqualTo(userRequest.getUserId());
    }


    @Test
    void toResponse() {
        UserDTO userDTO = easyRandom.nextObject(UserDTO.class);

        UserResponse expectedResponse = userMapper.toResponse(userDTO);

        assertThat(expectedResponse).usingRecursiveComparison().ignoringAllOverriddenEquals()
                .ignoringFields("message", "password")
                .isEqualTo(userDTO);

        assertThat(expectedResponse.getId()).isEqualTo(userDTO.getId());
    }

    @Test
    void toResponseWithoutMessage() {
        UserDTO userDTO = easyRandom.nextObject(UserDTO.class);

        UserResponseWithoutMessage expectedResponseW = userMapper.toResponseWithoutMessage(userDTO);

        assertThat(expectedResponseW).usingRecursiveComparison().ignoringAllOverriddenEquals()
                .ignoringFields("password")
                .isEqualTo(userDTO);

        assertThat(expectedResponseW.getId()).isEqualTo(userDTO.getId());
    }
}