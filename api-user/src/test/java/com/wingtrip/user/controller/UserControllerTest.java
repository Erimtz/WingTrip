package com.wingtrip.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wingtrip.user.controller.mapper.UserMapper;
import com.wingtrip.user.controller.request.UserRequest;
import com.wingtrip.user.controller.response.UserResponse;
import com.wingtrip.user.controller.response.UserResponseWithoutMessage;
import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.service.impl.UserServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {UserController.class})
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private UserServiceImpl userService;
    private UserDTO userDTO;
    private UserResponseWithoutMessage userResponseWithoutMessage;
    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);


        easyRandom = new EasyRandom();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(15L);
        userDTO.setName("Flor");
        userDTO.setLastname("Perez");
        userDTO.setAddress("calle 100");
        userDTO.setEmail("florecita@example.com");
        userDTO.setUsername("Florecita");

        userResponseWithoutMessage = new UserResponseWithoutMessage();
        userResponseWithoutMessage.setId(15L);
        userResponseWithoutMessage.setName("Flor");
        userResponseWithoutMessage.setLastname("Perez");
        userResponseWithoutMessage.setEmail("florecita@example.com");
        userResponseWithoutMessage.setUsername("Florecita");

    }

    @Test
    void getAllUsers() throws Exception {
        List<UserDTO> userDTOs = easyRandom.objects(UserDTO.class, 2).toList();
        Mockito.when(userService.getAllUsers()).thenReturn(userDTOs);

         mockMvc.perform(get("/api/v1/user/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }

    @Test
    void createUser() throws Exception {
        //Crear un objeto UserRequest con los datos del usuario
        UserRequest userRequest = new UserRequest(15L, "Flor", "Perez", "calle 100", "florecita@example.com", "Florecita", "1234");
        UserResponse userResponse = easyRandom.nextObject(UserResponse.class);
        UserDTO userDTO1 = easyRandom.nextObject(UserDTO.class);

        //Convertir el objeto UserRequest a JSON
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(userRequest);

        when(userMapper.toResponse(userDTO)).thenReturn(userResponse);
        when(userMapper.toRequest(userRequest)).thenReturn(userDTO1);
        when(userService.existByEmail(any())).thenReturn(false);
        when(userService.existByUsername(any())).thenReturn(false);
        when(userService.createUser(userDTO)).thenReturn(userDTO1);


        //Agregar el JSON al cuerpo de la solicitud del test
        mockMvc.perform(post("/api/v1/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    void findByUsername() throws Exception {
        String username = "UserTest";
        UserDTO userDTO1 = easyRandom.nextObject(UserDTO.class);
        UserResponse userResponse = easyRandom.nextObject(UserResponse.class);

        when(userService.findByUsername(username)).thenReturn(userDTO1);
        when(userMapper.toResponse(userDTO1)).thenReturn(userResponse);

        mockMvc.perform(get("/api/v1/user/profile/{username}", username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userResponse.getUsername()));
    }

    @Test
    void findUserById() throws Exception {
        Long id = 2L;
        UserDTO userDTO1 = easyRandom.nextObject(UserDTO.class);
        UserResponse userResponse = easyRandom.nextObject(UserResponse.class);

        when(userService.findUserById(id)).thenReturn(userDTO1);
        when(userMapper.toResponse(userDTO1)).thenReturn(userResponse);

        mockMvc.perform(get("/api/v1/user/find/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponse.getId()));
    }

    @Test
    void updateUserByUsername() throws Exception {
        String username = "UpdateTest";
        UserDTO userDTO1 = easyRandom.nextObject(UserDTO.class);
        UserRequest userRequest = easyRandom.nextObject(UserRequest.class);
        UserResponse userResponse = easyRandom.nextObject(UserResponse.class);

        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(userRequest);

        when(userService.updateUserByUsername(username, userRequest)).thenReturn(userDTO1);
        when(userMapper.toResponse(userDTO1)).thenReturn(userResponse);

        mockMvc.perform(put("/api/v1/user/update/username/{username}", username)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userResponse.getUsername()));
    }

    @Test
    void updateUserById() throws Exception {
        Long id = 2L;
        UserDTO userDTO1 = easyRandom.nextObject(UserDTO.class);
        UserRequest userRequest = easyRandom.nextObject(UserRequest.class);
        UserResponse userResponse = easyRandom.nextObject(UserResponse.class);

        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(userRequest);

        when(userService.updateUserById(id, userRequest)).thenReturn(userDTO1);
        when(userMapper.toResponse(userDTO1)).thenReturn(userResponse);

        mockMvc.perform(put("/api/v1/user/update/id/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponse.getId()));
    }

    @Test
    void deleteUserById() throws Exception {
        Long id = 2L;

        when(userService.deleteUserById(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/user/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}