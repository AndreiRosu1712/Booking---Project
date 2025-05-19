package com.example.Project_IP.controller;

import com.example.Project_IP.dto.LoginRequestDto;
import com.example.Project_IP.dto.LoginResponseDto;
import com.example.Project_IP.dto.RegisterRequestDto;
import com.example.Project_IP.dto.RegisterResponseDto;
import com.example.Project_IP.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) throws Exception {
        return userService.login(request);
    }

    @PostMapping("/register")
    public RegisterResponseDto register(@RequestBody RegisterRequestDto request) throws Exception {
        return userService.register(request);
    }

}
