package com.example.Project_IP.controller;

import com.example.Project_IP.model.Hotel;
import com.example.Project_IP.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotel> getAllHotels() throws Exception {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable String id) throws Exception {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/manager/{idManager}")
    public List<Hotel> getHotelsByManager(@PathVariable String idManager) throws Exception {
        return hotelService.getHotelsByManager(idManager);
    }

}
