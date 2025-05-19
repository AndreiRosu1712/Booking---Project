package com.example.Project_IP.service;

import com.example.Project_IP.dto.*;
import com.example.Project_IP.model.Conversation;
import com.example.Project_IP.model.Message;
import com.example.Project_IP.repository.ConversationRepository;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public void sendMessage(MessageRequestDto request) throws Exception {
        DocumentReference conversationRef = conversationRepository.getOrCreateConversation(request.getIdClient(), request.getIdHotel());
        String conversatieId = conversationRef.getId();

        boolean isNewConversation = !conversationRef.get().get().exists();

        if (isNewConversation) {
            Conversation conv = new Conversation();
            conv.setIdClient(request.getIdClient());
            conv.setIdHotel(request.getIdHotel());
            conv.setDataIncepere(new Date());
            conv.setUltimaActualizare(new Date());
            conv.setIdManager("..."); // setează managerul corect

            conversationRef.set(conv).get();

            Message autoReply = new Message();
            autoReply.setText("Salut, cu ce te putem să te ajutăm?");
            autoReply.setExpeditor("manager");
            autoReply.setIdHotel(request.getIdHotel());
            autoReply.setDataTrimitere(new Date());

            conversationRepository.getMessagesCollection(conversatieId).add(autoReply).get();
        }

        Message message = new Message();
        message.setText(request.getText());
        message.setIdHotel(request.getIdHotel());
        message.setExpeditor(request.getExpeditor());
        message.setIdExpeditor(request.getIdExpeditor()); // 🔹 adaugă asta
        message.setDataTrimitere(new Date());


        conversationRepository.getMessagesCollection(conversatieId).add(message).get();

        conversationRepository.actualizeazaUltimaData(conversatieId, com.google.cloud.Timestamp.of(message.getDataTrimitere()));
    }



    public List<MessageResponseDto> getMessages(String idClient, String idHotel) throws Exception {
        DocumentReference convRef = conversationRepository.getOrCreateConversation(idClient, idHotel);

        List<MessageResponseDto> responses = new ArrayList<>();
        for (var doc : conversationRepository.getMessagesCollection(convRef.getId()).orderBy("dataTrimitere").get().get().getDocuments()) {
            Message m = doc.toObject(Message.class);
            MessageResponseDto dto = new MessageResponseDto();
            dto.setText(m.getText());
            dto.setExpeditor(m.getExpeditor());
            dto.setDataTrimitere(m.getDataTrimitere());
            responses.add(dto);
        }

        return responses;
    }

    public List<MessageResponseDto> getMessagesForManager(String idManager, String idHotel) throws Exception {
        List<MessageResponseDto> messages = new ArrayList<>();

        CollectionReference conversations = FirestoreClient.getFirestore().collection("conversations");
        QuerySnapshot snapshot = conversations.whereEqualTo("idHotel", idHotel).whereEqualTo("idManager", idManager).get().get();

        for (QueryDocumentSnapshot conv : snapshot) {
            CollectionReference mesajeCol = conversationRepository.getMessagesCollection(conv.getId());
            for (var doc : mesajeCol.orderBy("dataTrimitere").get().get().getDocuments()) {
                Message m = doc.toObject(Message.class);
                MessageResponseDto dto = new MessageResponseDto();
                dto.setText(m.getText());
                dto.setExpeditor(m.getExpeditor());
                dto.setDataTrimitere(m.getDataTrimitere());
                messages.add(dto);
            }
        }
        return messages;
    }

    public List<Conversation> getConversationsByHotel(String idHotel) throws Exception {
        List<Conversation> conversations = new ArrayList<>();

        QuerySnapshot snapshot = FirestoreClient.getFirestore()
                .collection("conversations")
                .whereEqualTo("idHotel", idHotel)
                .get()
                .get();

        for (QueryDocumentSnapshot doc : snapshot) {
            conversations.add(doc.toObject(Conversation.class));
        }

        return conversations;
    }

}

//conversationRepository.actualizeazaUltimaData(conversatieId, com.google.cloud.Timestamp.of(message.getDataTrimitere()));