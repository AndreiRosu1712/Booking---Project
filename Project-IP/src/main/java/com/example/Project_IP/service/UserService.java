package com.example.Project_IP.service;

import com.example.Project_IP.entities.User;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUserById(String id) throws Exception {
        System.out.println("Looking for user with ID: " + id);

        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot snapshot = db.collection("users").document(id).get().get();

        if (snapshot.exists()) {
            return snapshot.toObject(User.class);
        } else {
            throw new RuntimeException("Utilizatorul nu există.");
        }
    }
}
