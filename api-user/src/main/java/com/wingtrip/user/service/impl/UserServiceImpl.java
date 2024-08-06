package com.wingtrip.user.service.impl;

import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.exception.MessageCode;
import com.wingtrip.user.exception.UserException;
import com.wingtrip.user.model.UserEntity;
import com.wingtrip.user.repository.UserRepository;
import com.wingtrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntity -> {
                    UserDTO userDTO = new UserDTO(userEntity);
                    userDTO.setId(userEntity.getUserId());
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) throws UserException {
        try {
            UserEntity userEntity = UserEntity.builder()
                    .name(userDTO.getName())
                    .lastname(userDTO.getLastname())
                    .address(userDTO.getAddress())
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();
            UserEntity saveEntity = userRepository.save(userEntity);
            return new UserDTO(saveEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserException(MessageCode.USER_NOT_CREATE);
        }
    }

    @Override
    public UserDTO findByUsername(String username) throws UserException {
        if (username == null) {
            throw new UserException(MessageCode.USERNAME_NULL);
        }

        Optional<UserEntity> entityOptional = userRepository.findByUsername(username);
        if (entityOptional.isPresent()) {
            return new UserDTO(entityOptional.get());
        } else {
            throw new UserException(MessageCode.USER_NOT_EXIST);
        }
    }

    @Override
    public UserDTO findUserById(Long userId) throws UserException {
        if (userId == null) {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        }

        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        }

        UserEntity userEntity = optionalUser.get();
        return new UserDTO(userEntity);
    }

    @Override
    public UserDTO updateUserById(Long userId, UserRequest userRequest) throws UserException {
        if (userId == null) {
            throw new UserException(MessageCode.USERNAME_NULL);
        }

        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        }

        UserEntity userEntity = optionalUser.get();
        userEntity.setName(userRequest.getName());
        userEntity.setLastname(userRequest.getLastname());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setAddress(userRequest.getAddress());

        UserEntity userEntity1 = userRepository.save(userEntity);


        return new UserDTO(userEntity1);
    }

    @Override
    public UserDTO updateUserByUsername(String username, UserRequest userRequest) throws UserException {
        if (username == null) {
            throw new UserException(MessageCode.USERNAME_NULL);
        }

        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            throw new UserException(MessageCode.USER_NOT_FOUND);
        }

        UserEntity userEntity1 = userEntity.get();
        userEntity1.setName(userRequest.getName());
        userEntity1.setLastname(userRequest.getLastname());
        userEntity1.setEmail(userRequest.getEmail());
        userEntity1.setUsername(userRequest.getUsername());
        userEntity1.setPassword(userRequest.getPassword());

        UserEntity updateEntity = userRepository.save(userEntity1);

        return new UserDTO(updateEntity);
    }

    @Override
    public boolean existByEmail(String email) throws UserException {
        if (email == null) {
            throw new UserException(MessageCode.EMAIL_USER_NOT_FOUND);
        } else {
            return userRepository.existsByEmail(email);
        }
    }

    @Override
    public boolean existByUsername(String username) throws UserException {
        if (username == null) {
            throw new UserException(MessageCode.USERNAME_NOT_FOUND);
        } else {
          return  userRepository.existsByUsername(username);
        }
    }

    @Override
    public boolean deleteUserById(Long userId) throws UserException {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        } else {
            userRepository.deleteById(userId);
            return true;
        }
    }

    public UserDTO getUserById(Long userId) throws UserException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(MessageCode.USER_ID_NOT_FOUND));
        return new UserDTO(userEntity);
    }

    public UserDTO getUserByUsername(String username) throws UserException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(MessageCode.USER_NOT_FOUND));
        return new UserDTO(userEntity);
    }

    public UserDTO getBookingId(Long userId) throws UserException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(MessageCode.USER_ID_NOT_FOUND));
        return new UserDTO(userEntity);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) throws UserException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw  new UserException(MessageCode.USER_NOT_FOUND);
        }

        UserEntity userEntity1 = userEntity.get();
        if (userDTO.getName() != null) {
            userEntity1.setName(userDTO.getName());
        }
        if (userDTO.getLastname() != null) {
            userEntity1.setLastname(userDTO.getLastname());
        }
        if (userDTO.getEmail() != null) {
            userEntity1.setEmail(userDTO.getEmail());
        }

        userRepository.save(userEntity1);
        return new UserDTO(userEntity1);
    }
}
