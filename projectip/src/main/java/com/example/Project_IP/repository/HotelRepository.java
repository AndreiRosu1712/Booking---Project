package com.example.Project_IP.repository;

import com.example.Project_IP.model.Hotel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HotelRepository {

    public List<Hotel> getAllHotels() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("hotels").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Hotel> hotels = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            Hotel hotel = doc.toObject(Hotel.class);
            hotel.setId(doc.getId());
            hotels.add(hotel);
        }
        return hotels;
    }

    public Hotel getHotelById(String id) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot doc = db.collection("hotels").document(id).get().get();
        if (doc.exists()) {
            Hotel hotel = doc.toObject(Hotel.class);
            hotel.setId(doc.getId());
            return hotel;
        } else {
            throw new RuntimeException("Hotelul nu există.");
        }
    }

    public List<Hotel> findHotelsByManagerId(String idManager) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference hotels = db.collection("hotels");

        Query query = hotels.whereEqualTo("idManager", idManager);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Hotel> result = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            Hotel hotel = doc.toObject(Hotel.class);
            hotel.setId(doc.getId());
            result.add(hotel);
        }

        return result;
    }



}