package com.techmanage.serviceImpl;

import com.techmanage.entity.User;
import com.techmanage.entity.UserType;
import com.techmanage.exception.UserNotFoundException;
import com.techmanage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setFullName("Usuário Teste");
        mockUser.setEmail("teste@example.com");
        mockUser.setPhone("+55 11 99999-9999");
        mockUser.setBirthDate(LocalDate.of(2000, 1, 1));
        mockUser.setUserType(UserType.EDITOR);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        User saved = userService.createUser(mockUser);
        assertEquals("Usuário Teste", saved.getFullName());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testGetUserById_existing() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        User found = userService.getUserById(1L);
        assertEquals("teste@example.com", found.getEmail());
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        userService.deleteUser(1L);
        verify(userRepository).delete(mockUser);
    }
    @Test
    void testUpdateUser() {
        User updatedData = new User();
        updatedData.setFullName("Usuário atualizado");
        updatedData.setEmail("novoemail@example.com");
        updatedData.setPhone("+55 11 98888-7777");
        updatedData.setBirthDate(LocalDate.of(1995, 5, 15));
        updatedData.setUserType(UserType.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedData);

        User result = userService.updateUser(1L, updatedData);

        assertEquals("Usuário atualizado", result.getFullName());
        assertEquals("novoemail@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }
    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(mockUser));
        var users = userService.getAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(mockUser.getEmail(), users.get(0).getEmail());
    }
}