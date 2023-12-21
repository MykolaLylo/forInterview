package com.demo_books_shop.service.impl;

import com.demo_books_shop.exception.UserAlreadyExistException;
import com.demo_books_shop.exception.UserNotFoundException;
import com.demo_books_shop.models.User;
import com.demo_books_shop.repository.UserRepository;
import com.demo_books_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import java.util.*;

@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) throws UserAlreadyExistException {
        Optional<User> findUser = userRepository.findUserByEmail(user.getEmail());
        if (findUser.isPresent()) {
            throw new UserAlreadyExistException("This user already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(@AuthenticationPrincipal User user,
                       String name, String surname, String email, String password) {

        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void delete(@AuthenticationPrincipal User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User didn't found");
        }
        return user.get();
    }
}

