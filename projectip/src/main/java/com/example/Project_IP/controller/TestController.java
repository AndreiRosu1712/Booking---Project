package com.example.Project_IP.controller;

import com.example.Project_IP.service.FirestoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final FirestoreService service;

    public TestController(FirestoreService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test() throws Exception {
        service.testSave();
        return "Saved to Firestore!";
    }
}