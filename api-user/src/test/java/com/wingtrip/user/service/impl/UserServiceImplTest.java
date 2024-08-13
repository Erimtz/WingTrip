package com.wingtrip.user.service.impl;

import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.model.UserEntity;
import com.wingtrip.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    public UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllUsers() {
//        UserDTO userDTO1 = new UserDTO();
//        UserEntity user = new UserEntity();
//        Long userId = 1L;
    }

    @Test
    void createUser() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void updateUserById() {
    }

    @Test
    void updateUserByUsername() {
    }

    @Test
    void existByEmail() {
    }

    @Test
    void existByUsername() {
    }

    @Test
    void deleteUserById() {
    }
}