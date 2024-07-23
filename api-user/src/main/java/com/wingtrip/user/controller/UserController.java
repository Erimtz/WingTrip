package com.wingtrip.user.controller;

import com.wingtrip.user.controller.mapper.UserMapper;
import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.controller.response.UserResponse;
import com.wingtrip.user.controller.response.UserResponseWithoutMessage;
import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.exception.MessageCode;
import com.wingtrip.user.exception.UserException;
import com.wingtrip.user.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "User API", description = "API for managing users")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserMapper userMapper;

    private final UserServiceImpl userService;

    @Operation(summary = "Get all the users")
    @GetMapping("/get-all")
    public ResponseEntity<List<UserResponseWithoutMessage>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        List<UserResponseWithoutMessage> userResponses = userDTOS.stream()
                .map(userMapper::toResponseWithoutMessage)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }

    @Operation(summary = "Created new user")
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws UserException {
        //Mappear de request a dto
        UserDTO dtoToRequest = userMapper.toRequest(userRequest);

        log.info("Checking if email exists {}:", dtoToRequest.getEmail());
        if (userService.existByEmail(dtoToRequest.getEmail())) {
            throw new UserException(MessageCode.EMAIL_CREATE_BEFORE);
        }

        if (userService.existByUsername(dtoToRequest.getUsername())) {
            throw new UserException(MessageCode.USERNAME_CREATE_BEFORE);
        }
        UserDTO createdUser = userService.createUser(dtoToRequest);
        UserResponse userResponse = userMapper.toResponse(createdUser);
        userResponse.setMessage("¡Created user successfully!");

        log.info("Created user successfully with data: {}", userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Search user by username")
    @GetMapping("/profile/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username) throws UserException {
        UserDTO userDTO = userService.findByUsername(username);
        UserResponse userResponse = userMapper.toResponse(userDTO);
        userResponse.setMessage("Find user successfully with username: " + username);

        log.info("Find user by username successfully with data: {}" + userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Search user by ID")
    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) throws UserException {
        UserDTO userDTO = userService.findUserById(id);
        UserResponse userResponse = userMapper.toResponse(userDTO);
        userResponse.setMessage("Find user by ID successfully with ID: " + id);

        log.info("Find user by ID successfully with data: {}", userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Update the user by username")
    @PutMapping("/update/username/{username}")
    public ResponseEntity<UserResponse> updateUserByUsername(@PathVariable String username, @RequestBody UserRequest userRequest) throws UserException {
        UserDTO updateUser = userService.updateUserByUsername(username, userRequest);
        UserResponse userResponse = userMapper.toResponse(updateUser);
        userResponse.setMessage("Update user successfully with username: " + username);

        log.info("Update account successfully with data: {}", username);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Update the user by ID")
    @PutMapping("/update/id/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long id, @RequestBody UserRequest userRequest) throws UserException {
        UserDTO toRequest = userService.updateUserById(id, userRequest);
        UserResponse userResponse = userMapper.toResponse(toRequest);
        userResponse.setMessage("Update user by ID successfully with ID: " + id);

        log.info("Update user by ID account successfullyu with data: {}" + userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id) throws UserException {
        boolean isDeleted = userService.deleteUserById(id);

        if (isDeleted) {
            log.info("Delete by user ID successfully with data: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            throw new UserException(MessageCode.USER_DELETE_FAILED);
        }

    }

    @Operation(summary = "Find user by ID with feign")
    @GetMapping("/feign-user-id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) throws UserException {
        UserDTO userDTO = userService.getUserById(id);
        UserResponse userResponse = userMapper.toResponse(userDTO);
        if (userDTO != null) {
            return ResponseEntity.ok(userResponse);
        } else {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        }
    }
    @Operation(summary = "Find user by username with feign")
    @GetMapping("/feign-username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) throws UserException {
        UserDTO userDTO = userService.getUserByUsername(username);
        UserResponse userResponse = userMapper.toResponse(userDTO);
        if (userDTO != null) {
            return ResponseEntity.ok(userResponse);
        } else {
            throw new UserException(MessageCode.USER_NOT_FOUND);
        }
    }
    @Operation(summary = "Find booking ID by user ID with feign")
    @GetMapping("/feign-booking/{id}")
    public ResponseEntity<UserResponse> getBookingId(@PathVariable Long id) throws UserException{
        UserDTO userDTO = userService.getBookingId(id);
        UserResponse userResponse = userMapper.toResponse(userDTO);
        if (userDTO != null) {
            return ResponseEntity.ok(userResponse);
        } else {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        }
    }
}
