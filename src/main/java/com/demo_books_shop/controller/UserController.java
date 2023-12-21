package com.demo_books_shop.controller;

import com.demo_books_shop.exception.UserAlreadyExistException;
import com.demo_books_shop.exception.UserNotFoundException;
import com.demo_books_shop.models.Bucket;
import com.demo_books_shop.models.Role;
import com.demo_books_shop.models.User;
import com.demo_books_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registration/{status}")
    public String registrationUser(@RequestParam String name,
                                   @RequestParam String surname,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @PathVariable boolean status,
                                   Model model){
        Set<Role> roles = new HashSet<>(List.of(Role.ROLE_USER));
        User user = new User(name,surname,email,password,roles);
        user.setActive(true);
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        user.setBucket(bucket);
        model.addAttribute("user",status);
            try {
                userService.save(user);
            } catch (UserAlreadyExistException e) {
                return "registration";
        }
        return "redirect:/login";
    }

    @PostMapping("/login/processing")
    public String getUser(@RequestParam String email){
        try {
         User user = userService.getUserByEmail(email);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "/login";
        }
        return "redirect: /welcomePage";
    }
    @PostMapping("/update")
    public String updateUser(@AuthenticationPrincipal User user,
                             String name, String surname, String email, String password){
        userService.update(user,name,surname,email,password);
        return "redirect:/welcomePage";
    }

    @GetMapping("/userPage")
    public String getUserPage(@AuthenticationPrincipal User user,Model model){
            model.addAttribute("user",user);
        return "user";
    }
    @PostMapping("/deleteAccount")
    public RedirectView delete(@AuthenticationPrincipal User user){
        userService.delete(user);
        return new RedirectView("/login") ;
        }

    @GetMapping("/update_delete")
    public String updateDeletePage(@AuthenticationPrincipal User user,Model model){
        model.addAttribute("user",user);
        return "update-delete-user";
    }
}

