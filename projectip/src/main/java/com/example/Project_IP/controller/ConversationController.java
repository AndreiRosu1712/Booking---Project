package com.example.Project_IP.controller;

import com.example.Project_IP.dto.MessageRequestDto;
import com.example.Project_IP.dto.MessageResponseDto;
import com.example.Project_IP.model.Conversation;
import com.example.Project_IP.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody MessageRequestDto request) throws Exception {
        conversationService.sendMessage(request);
    }

    @GetMapping("/{idClient}/{idHotel}")
    public List<MessageResponseDto> getMessages(@PathVariable String idClient, @PathVariable String idHotel) throws Exception {
        return conversationService.getMessages(idClient, idHotel);
    }

    @GetMapping("/hotel/{idHotel}")
    public List<Conversation> getConversationsByHotel(@PathVariable String idHotel) throws Exception {
        return conversationService.getConversationsByHotel(idHotel);
    }

    @GetMapping("/hotel/{idHotel}/client/{idClient}")
    public List<MessageResponseDto> getMessagesForHotelClient(@PathVariable String idHotel, @PathVariable String idClient) throws Exception {
        return conversationService.getMessages(idClient, idHotel);
    }
}
