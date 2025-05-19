package com.example.Project_IP.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ConversationDto {
    private String idClient;
    private String idManager;
    private String idHotel;
    private Date dataIncepere;
    private Date ultimaActualizare;
}
