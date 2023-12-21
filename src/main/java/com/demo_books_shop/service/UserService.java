package com.demo_books_shop.service;

import com.demo_books_shop.exception.UserAlreadyExistException;
import com.demo_books_shop.exception.UserNotFoundException;
import com.demo_books_shop.models.Books;
import com.demo_books_shop.models.Bucket;
import com.demo_books_shop.models.Role;
import com.demo_books_shop.models.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

public interface UserService  {
    void save(User user) throws UserAlreadyExistException;
    void update(@AuthenticationPrincipal User user,
                String name, String surname, String email, String password);
    User getUserByEmail(String email) throws UserNotFoundException;
    void delete(@AuthenticationPrincipal User user);


}

