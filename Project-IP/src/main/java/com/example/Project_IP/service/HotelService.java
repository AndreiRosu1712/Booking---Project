package com.example.Project_IP.service;

import com.example.Project_IP.model.Hotel;
import com.example.Project_IP.repository.HotelRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() throws Exception {
        List<Hotel> hotels = hotelRepository.getAllHotels();
        Firestore db = FirestoreClient.getFirestore();

        for (Hotel hotel : hotels) {
            DocumentSnapshot managerDoc = db.collection("users").document(hotel.getIdManager()).get().get();
            if (managerDoc.exists()) {
                String numeComplet = managerDoc.getString("nume") + " " + managerDoc.getString("prenume");
                hotel.setNumeManager(numeComplet);
            }
        }
        return hotels;
    }

    public Hotel getHotelById(String id) throws Exception {
        Hotel hotel = hotelRepository.getHotelById(id);
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot managerDoc = db.collection("users").document(hotel.getIdManager()).get().get();
        if (managerDoc.exists()) {
            String numeComplet = managerDoc.getString("nume") + " " + managerDoc.getString("prenume");
            hotel.setNumeManager(numeComplet);
        }
        return hotel;
    }

    public List<Hotel> getHotelsByManager(String idManager) throws Exception {
        return hotelRepository.findHotelsByManagerId(idManager);
    }

}
