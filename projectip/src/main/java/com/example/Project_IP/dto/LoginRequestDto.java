package com.example.Project_IP.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String identifier; // poate fi username sau email
    private String parola;
}
