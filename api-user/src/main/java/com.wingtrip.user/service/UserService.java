package com.wingtrip.user.service;

import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.exception.UserException;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO) throws UserException;
    UserDTO findByUsername(String username) throws UserException;
    UserDTO findUserById(Long userId) throws UserException;
    UserDTO updateUserById(Long userId, UserRequest userRequest) throws UserException;
    UserDTO updateUserByUsername(String username, UserRequest userRequest) throws UserException;
    boolean existByEmail(String email) throws UserException;
    boolean existByUsername(String username) throws UserException;
    boolean deleteUserById(Long userId) throws UserException;
}
