package com.demo_books_shop.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.demo_books_shop.exception.UserAlreadyExistException;
import com.demo_books_shop.models.User;
import com.demo_books_shop.repository.UserRepository;
import com.demo_books_shop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl yourService;
    private static final User user = mock(User.class);
    private static final String email = "test@gmail.com";
    private static final String password = "password";

    @BeforeAll
    public static void setup(){
        when(user.getEmail()).thenReturn(email);
        when(user.getPassword()).thenReturn(password);
    }
    @Test
    void testSave() throws UserAlreadyExistException {
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class, () -> yourService.save(user));

        verify(userRepository, times(1)).findUserByEmail(user.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testSaveWithNonExistingUser() throws UserAlreadyExistException {

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn(password);

        yourService.save(user);

        verify(userRepository,times(1)).findUserByEmail(user.getEmail());
        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
    @Test
    void updateTest(){
        String name = "name";
        String surname ="surname";
        String newEmail = "new.test@gmail.com";
        String newPassword = "newPassword";

        when(user.getName()).thenReturn(name);
        when(user.getSurname()).thenReturn(surname);
        when(user.getEmail()).thenReturn(newEmail);
        when(user.getPassword()).thenReturn(newPassword);

        yourService.update(user,name,surname,newEmail,newPassword);

    }
}
