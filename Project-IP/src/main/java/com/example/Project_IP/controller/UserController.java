package com.example.Project_IP.controller;

import com.example.Project_IP.entities.User;
import com.example.Project_IP.service.FirestoreService;
import com.example.Project_IP.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) throws Exception {
        return userService.getUserById(id);
    }
}
