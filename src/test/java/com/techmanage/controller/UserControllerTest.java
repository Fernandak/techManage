package com.techmanage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmanage.entity.User;
import com.techmanage.entity.UserType;
import com.techmanage.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    private User getMockUser() {
        User user = new User();
        user.setId(1L);
        user.setFullName("João Teste");
        user.setEmail("joao@example.com");
        user.setPhone("+55 11 98888-8888");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setUserType(UserType.ADMIN);
        return user;
    }

    @Test
    void testCreateUser() {
        User mockUser = getMockUser();
        when(userService.createUser(any(User.class))).thenReturn(mockUser);

        ResponseEntity<User> response = userController.createUser(mockUser);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("joao@example.com", response.getBody().getEmail());
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(getMockUser()));

        ResponseEntity<List<User>> response = userController.getAllUsers();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1L)).thenReturn(getMockUser());

        ResponseEntity<User> response = userController.getUserById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("João Teste", response.getBody().getFullName());
    }

    @Test
    void testUpdateUser() {
        User updated = getMockUser();
        updated.setFullName("João Atualizado");

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updated);

        ResponseEntity<User> response = userController.updateUser(1L, updated);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("João Atualizado", response.getBody().getFullName());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<String> response = userController.deleteUser(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Usuário deletado com sucesso", response.getBody());
    }
}