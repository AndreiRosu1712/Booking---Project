package com.example.Project_IP.model;

import lombok.Data;
import java.util.Date;

@Data
public class Conversation {
    private String idClient;
    private String idManager;
    private String idHotel;
    private Date dataIncepere;
    private Date ultimaActualizare;
}
