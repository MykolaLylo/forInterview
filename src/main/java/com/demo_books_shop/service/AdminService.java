package com.demo_books_shop.service;

import com.demo_books_shop.models.Role;
import com.demo_books_shop.models.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Set;

public interface AdminService {
    List<User> getAllUsers();

    @Transactional
    void blockStatus(boolean active, int id);

    User setRole(Set<Role> role, String email);
}
