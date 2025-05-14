package com.example.Project_IP.dto;

import lombok.Data;
import java.util.Date;

@Data
public class MessageResponseDto {
    private String text;
    private String expeditor;
    private Date dataTrimitere;
}
