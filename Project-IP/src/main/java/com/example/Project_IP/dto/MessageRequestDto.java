package com.example.Project_IP.dto;

import lombok.Data;

@Data
public class MessageRequestDto {
    private String idClient;
    private String idHotel;
    private String text;
    private String expeditor; // "client" sau "manager"
    private String idExpeditor;
}
