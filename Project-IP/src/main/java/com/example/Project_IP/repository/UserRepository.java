package com.example.Project_IP.repository;

import com.example.Project_IP.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository {

    private final Firestore db = FirestoreClient.getFirestore();

    public DocumentSnapshot findByEmailOrUsername(String identifier) throws ExecutionException, InterruptedException {
        CollectionReference usersRef = db.collection("users");

        ApiFuture<QuerySnapshot> usernameQuery = usersRef.whereEqualTo("username", identifier).get();
        QuerySnapshot usernameSnapshot = usernameQuery.get();

        if (!usernameSnapshot.isEmpty()) {
            return usernameSnapshot.getDocuments().get(0);
        }

        ApiFuture<QuerySnapshot> emailQuery = usersRef.whereEqualTo("mail", identifier).get();
        QuerySnapshot emailSnapshot = emailQuery.get();

        if (!emailSnapshot.isEmpty()) {
            return emailSnapshot.getDocuments().get(0);
        }

        return null;
    }


    public User findById(String id) throws Exception {
        DocumentSnapshot snapshot = db.collection("users").document(id).get().get();
        if (snapshot.exists()) {
            return snapshot.toObject(User.class);
        } else {
            return null;
        }
    }

    public boolean existsByEmailOrUsername(String email, String username) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        boolean emailExists = !db.collection("users")
                .whereEqualTo("mail", email)
                .get().get().isEmpty();

        boolean usernameExists = !db.collection("users")
                .whereEqualTo("username", username)
                .get().get().isEmpty();

        return emailExists || usernameExists;
    }

    public void saveUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("users").add(user).get(); // se generează automat un ID
    }
}
