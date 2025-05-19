package com.example.Project_IP.controller;

import com.example.Project_IP.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.List;



@RestController
@RequestMapping("/bookigs")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public String createBooking(@RequestBody Map<String, Object> data) throws Exception {
        bookingService.saveBooking(data);
        return "Rezervare salvată cu succes!";
    }

    @GetMapping("/client/{idClient}")
    public List<Map<String, Object>> getBookingsByClient(@PathVariable String idClient) throws Exception {
        return bookingService.getBookingsByClient(idClient);
    }

}
