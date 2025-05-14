// Hotel.java (model)
package com.example.Project_IP.model;

import lombok.Data;

@Data
public class Hotel {
    private String id;
    private String nume;
    private String descriere;
    private String locatie;
    private String idManager;
    private String numeManager; // nou camp pentru afisare frumoasa

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}