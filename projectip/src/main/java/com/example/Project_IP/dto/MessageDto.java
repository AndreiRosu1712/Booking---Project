package com.example.Project_IP.dto;

public class MessageDto {
    private String idHotel;
    private String idClient;
    private String idExpeditor;
    private String text;
    private String expeditor; // "client" sau "manager"

    public String getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdExpeditor() {
        return idExpeditor;
    }

    public void setIdExpeditor(String idExpeditor) {
        this.idExpeditor = idExpeditor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExpeditor() {
        return expeditor;
    }

    public void setExpeditor(String expeditor) {
        this.expeditor = expeditor;
    }
}
