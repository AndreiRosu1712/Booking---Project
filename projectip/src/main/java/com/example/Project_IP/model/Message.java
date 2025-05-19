package com.example.Project_IP.model;

import lombok.Data;
import java.util.Date;

@Data
public class Message {
    private String text;
    private String idHotel;
    private String expeditor; // poate fi "client" sau "manager"
    private Date dataTrimitere;
    private String idExpeditor;

}
