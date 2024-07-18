package com.wingtrip.user.service;

import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.exception.UserException;
import com.wingtrip.user.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO) throws UserException;
    Optional<UserDTO> findByUsername(String username) throws UserException;
    Optional<UserDTO> findUserById(Long userId) throws UserException;
    UserDTO updateUserById(Long userId) throws UserException;
    UserDTO updateUserEntity(String username, UserRequest userRequest) throws UserException;
    boolean existByEmail(String email) throws UserException;
    boolean existByUsername(String username) throws UserException;
    boolean deleteUserById(Long userId) throws UserException;
    UserDTO convertTo(UserEntity userEntity);
}
