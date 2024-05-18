package com.adil.blog.service;

import com.adil.blog.entity.User;
import com.adil.blog.entity.UserRole;
import com.adil.blog.exception.DuplicateEmailException;
import com.adil.blog.exception.InvalidEmailException;
import com.adil.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .role(UserRole.ROLE_USER)
                .build();
    }

    @Test
    void testRegister_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        userService.register(user);

        verify(userRepository, times(1)).save(user);
        assertEquals("encryptedPassword", user.getPassword());
        assertEquals(UserRole.ROLE_USER, user.getRole());
    }

    @Test
    void testRegister_InvalidEmail() {
        user.setEmail("invalidEmail");

        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            userService.register(user);
        });

        assertEquals("Votre est email est invalide", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void testRegister_DuplicateEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, () -> {
            userService.register(user);
        });

        assertEquals("Votre mail existe déjà", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User foundUser = userService.loadUserByUsername("test@example.com");

        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("test@example.com");
        });

        assertEquals("Aucun utilisateur ne correspond à cette identifiant", exception.getMessage());
    }

    @Test
    void testFindById_UserExists() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void testFindById_UserDoesNotExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findById(1L);

        assertFalse(foundUser.isPresent());
    }
}