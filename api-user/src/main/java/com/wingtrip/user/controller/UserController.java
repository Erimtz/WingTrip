package com.wingtrip.user.controller;

import com.wingtrip.user.controller.mapper.UserMapper;
import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.controller.response.UserResponse;
import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.exception.MessageCode;
import com.wingtrip.user.exception.UserException;
import com.wingtrip.user.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(name = "/api/v1/user")
public class UserController {

    private final UserMapper userMapper;

    private final UserServiceImpl userService;

    @Operation(summary= "Get all the users")
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Transactional
    @Operation(summary= "Created new user")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) throws UserException {
           //Mappear de request a dto
            UserDTO userRequestToDto = userMapper.toUser(userRequest);

            if (userService.existByEmail(userRequestToDto.getEmail())) {
                throw new UserException(MessageCode.EMAIL_CREATE_BEFORE);
            }

            if (userService.existByUsername(userRequestToDto.getUsername())) {
                throw new UserException(MessageCode.USERNAME_CREATE_BEFORE);
            }
            UserDTO userEntity1 = userService.createUser(userRequestToDto);

            return ResponseEntity.ok(userEntity1.getUsername()+  "tu usuario se creo bien!");
    }

    @Operation(summary= "Search user by username")
    @GetMapping("/profile/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username) throws UserException {
            Optional<UserDTO> toRequest = userService.findByUsername(username);

            UserResponse userResponse = userMapper.toResponse(toRequest);
            return ResponseEntity.ok(userResponse);
    }

    @Operation(summary= "Search user by ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long userId) throws UserException {
            Optional<UserDTO> toRequest = userService.findUserById(userId);

            UserResponse userResponse = userMapper.toResponse(toRequest);
            return  ResponseEntity.ok(userResponse);
    }
    @Operation(summary= "Update the user by username")
    @PutMapping("/update/{username}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable  String username, @RequestBody UserRequest userRequest) throws UserException {
            UserDTO toRequest = userService.updateUserEntity(username, userRequest);

            UserResponse userResponse = userMapper.toResponse(toRequest);
            return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Update the user by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long userId) throws UserException {
        UserDTO toRequest = userService.updateUserById(userId);

        UserResponse userResponse = userMapper.toResponse(toRequest);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary= "Delete user by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long userId) throws UserException {
            boolean isDeleted = userService.deleteUserById(userId);

            if (isDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                throw new UserException(MessageCode.USER_DELETE_FAILED);
            }

    }

}
