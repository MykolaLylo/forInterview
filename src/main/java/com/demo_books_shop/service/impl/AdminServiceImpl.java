package com.demo_books_shop.service.impl;

import com.demo_books_shop.models.Role;
import com.demo_books_shop.models.User;
import com.demo_books_shop.repository.UserRepository;
import com.demo_books_shop.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@EnableTransactionManagement
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public void blockStatus(boolean active, int id){
        userRepository.updateActiveStatus(active,id);
    }

    @Override
    public User setRole(Set<Role> role, String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        user.ifPresent(present -> present.setRoles(role));
        return userRepository.save(user.orElse(new User()));
    }
}
