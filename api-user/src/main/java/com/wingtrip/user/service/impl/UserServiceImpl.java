package com.wingtrip.user.service.impl;

import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.exception.MessageCode;
import com.wingtrip.user.exception.UserException;
import com.wingtrip.user.model.UserEntity;
import com.wingtrip.user.repository.UserRepository;
import com.wingtrip.user.service.UserService;
import jakarta.transaction.Transactional;
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
                .map(this::convertTo)
                .collect(Collectors.toList());
    }
    @Transactional
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
            userRepository.save(userEntity);
            return convertTo(userEntity);
        } catch (Exception e) {
            throw new UserException(MessageCode.USER_NOT_FOUND);
        }
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) throws UserException {
        if (username == null) {
            throw new UserException(MessageCode.USERNAME_NULL);
        } else {
            return userRepository.findByUsername(username);
        }
    }

    @Override
    public Optional<UserDTO> findUserById(Long userId) throws UserException {
        if (userId == null) {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        }

        Optional<UserEntity> optionalUser1 = userRepository.findById(userId);
        if (optionalUser1.isEmpty()) {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        }
        return Optional.of(new UserDTO(optionalUser1));
    }

    @Override
    public UserDTO updateUserById(Long userId) throws UserException {
        if (userId == null) {
            throw new UserException(MessageCode.USERNAME_NULL);
        }

        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException(MessageCode.USER_ID_NOT_FOUND);
        }

        UserEntity userEntity = optionalUser.get();
        userEntity.setName(userEntity.getName());
        userEntity.setLastname(userEntity.getLastname());
        userEntity.setEmail(userEntity.getEmail());
        userEntity.setUsername(userEntity.getUsername());
        userEntity.setPassword(userEntity.getPassword());
        userEntity.setAddress(userEntity.getAddress());

        userRepository.save(userEntity);


        return convertTo(userEntity);
    }

    @Override
    public UserDTO updateUserEntity(String username, UserRequest userRequest) throws UserException {
        if (username == null) {
            throw new UserException(MessageCode.USERNAME_NULL);
        }

        Optional<UserDTO> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            throw new UserException(MessageCode.USER_NOT_FOUND);
        }

        UserEntity userEntity1 = userEntity.get().toEntity();

        userEntity1.setUsername(userRequest.getUsername());
        userEntity1.setPassword(userRequest.getPassword());
        userRepository.save(userEntity1);

        return new UserDTO(userEntity1);
    }

    @Override
    public boolean existByEmail(String email) throws UserException {
        if (email == null) {
            throw new UserException(MessageCode.EMAIL_USER_NOT_FOUND);
        } else {
            return userRepository.existByEmail(email);
        }
    }

    @Override
    public boolean existByUsername(String username) throws UserException {
        if (username == null) {
            throw new UserException(MessageCode.USERNAME_NOT_FOUND);
        } else {
            userRepository.findByUsername(username);
            return true;
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

    @Override
    public UserDTO convertTo(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getUserId(),
                userEntity.getName(),
                userEntity.getLastname(),
                userEntity.getAddress(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getPassword()
        );
    }
}
