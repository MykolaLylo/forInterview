package com.demo_books_shop.controller;

import com.demo_books_shop.exception.UserAlreadyExistException;
import com.demo_books_shop.exception.UserNotFoundException;
import com.demo_books_shop.models.Role;
import com.demo_books_shop.models.User;
import com.demo_books_shop.service.AdminService;
import com.demo_books_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public AdminController(UserService userService,AdminService adminService) {
        this.userService = userService ;
        this.adminService = adminService;
    }

    @PostMapping("/create/user")
    public String create(@RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam Set<Role> role,
                         Model model){

            User user = new User(name,surname,email,password,role);
            model.addAttribute("user",user);
        try {
            userService.save(user);
        } catch (UserAlreadyExistException e) {
            e.printStackTrace();
        }
        return "redirect: /creatingPage?success";
    }

    @GetMapping("/allUsers")
    public String getAll (Model model){
       List<User> users = adminService.getAllUsers();
       model.addAttribute("users",users);
       return "users";
    }
    @GetMapping("/user-info/{email}")
    public String userInfo(@PathVariable String email, Model model){
        try {
            User user = userService.getUserByEmail(email);
            model.addAttribute("user",user);
            return "user-info";
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/blockAccount")
    public RedirectView block(@RequestParam boolean active, @RequestParam int id){
        adminService.blockStatus(active,id);
        return new RedirectView("/allUsers");
    }
    @PostMapping ("/roleChanging")
    public RedirectView changeRole(@RequestParam Set<Role> role, @RequestParam String email) {
        adminService.setRole(role,email);
        return new RedirectView("/allUsers");
    }

}
