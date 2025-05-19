package com.example.Project_IP.websocket;

import com.example.Project_IP.dto.MessageDto;
import com.example.Project_IP.model.Message;
import com.example.Project_IP.repository.ConversationRepository;
import com.google.cloud.firestore.DocumentReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(MessageDto messageDto) throws Exception {
        // Obține conversația
        DocumentReference conversationRef = conversationRepository.getOrCreateConversation(
                messageDto.getIdClient(),
                messageDto.getIdHotel()
        );

        // Creează mesajul
        Message message = new Message();
        message.setText(messageDto.getText());
        message.setIdHotel(messageDto.getIdHotel());
        message.setExpeditor(messageDto.getExpeditor());
        message.setIdExpeditor(messageDto.getIdExpeditor());
        message.setDataTrimitere(new Date());

        // Salvează în Firestore
        conversationRepository.getMessagesCollection(conversationRef.getId()).add(message).get();

        // Trimite mesajul prin WebSocket tuturor abonaților la conversație
        messagingTemplate.convertAndSend(
                "/topic/conversations/" + messageDto.getIdHotel() + "/" + messageDto.getIdClient(),
                message
        );
    }
}
