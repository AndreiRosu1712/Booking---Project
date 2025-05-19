package com.example.Project_IP.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class FirestoreService {
    public void testSave() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> data = Map.of("message", "hello", "time", new Date());
        db.collection("test").document("demo").set(data);
    }
}