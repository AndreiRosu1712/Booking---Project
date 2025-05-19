package com.example.Project_IP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // creează constructor cu toți cei 3 parametri
@NoArgsConstructor  // constructor fără parametri (necesar pentru JSON mapping)
public class LoginResponseDto {
    private String mesaj;
    private String rol;
    private String id;
}
