package com.example.Project_IP.entities;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class User {
    private String mail;
    private String nume;
    private String prenume;
    private String parola;
    private String rol;
    private String username;

    // Optional pentru client
    private Float sold;

    // Optional pentru manager
    private List<Map<String, String>> idHotels;
}
