package com.example.Project_IP.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class ConversationRepository {

    private final Firestore db = FirestoreClient.getFirestore();

    public DocumentReference getOrCreateConversation(String idClient, String idHotel) throws ExecutionException, InterruptedException {
        CollectionReference conversations = db.collection("conversations");

        Query query = conversations.whereEqualTo("idClient", idClient).whereEqualTo("idHotel", idHotel);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        if (!documents.isEmpty()) {
            return documents.get(0).getReference();
        }

        // creează conversația
        DocumentReference newConvRef = conversations.document();
        return newConvRef;
    }

    public CollectionReference getMessagesCollection(String conversationId) {
        return db.collection("conversations").document(conversationId).collection("mesaje");
    }

    public void actualizeazaUltimaData(String conversatieId, Timestamp data) {
        DocumentReference docRef = db.collection("conversations").document(conversatieId);
        Map<String, Object> update = new HashMap<>();
        update.put("ultimaActualizare", data.toDate());
        docRef.update(update);
    }

}
