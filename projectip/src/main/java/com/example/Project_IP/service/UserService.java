package com.example.Project_IP.service;

import com.example.Project_IP.dto.LoginRequestDto;
import com.example.Project_IP.dto.LoginResponseDto;
import com.example.Project_IP.dto.RegisterRequestDto;
import com.example.Project_IP.dto.RegisterResponseDto;
import com.example.Project_IP.model.User;
import com.example.Project_IP.repository.UserRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String id) {
        try {
            User user = userRepository.findById(id);
            if (user == null) {
                throw new RuntimeException("Utilizatorul nu există.");
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Eroare la căutarea utilizatorului: " + e.getMessage());
        }
    }

    public LoginResponseDto login(LoginRequestDto request) throws Exception {
        DocumentSnapshot snapshot = userRepository.findByEmailOrUsername(request.getIdentifier());

        System.out.println("Login attempt for: " + request.getIdentifier());
        System.out.println("Parola introdusă: " + request.getParola());
        System.out.println("Snapshot null? " + (snapshot == null));

        if (snapshot == null) {
            throw new RuntimeException("Utilizatorul nu a fost găsit.");
        }

        User user = snapshot.toObject(User.class);
        if (!user.getParola().equals(request.getParola())) {
            throw new RuntimeException("Parolă incorectă.");
        }

        return new LoginResponseDto("Autentificare reușită!", user.getRol(), snapshot.getId());
    }

    public RegisterResponseDto register(RegisterRequestDto request) throws Exception {
        if (userRepository.existsByEmailOrUsername(request.getMail(), request.getUsername())) {
            throw new RuntimeException("Email sau username deja folosit.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setParola(request.getParola());
        user.setMail(request.getMail());
        user.setNume(request.getNume());
        user.setPrenume(request.getPrenume());
        user.setRol(request.getRol());

        if ("client".equalsIgnoreCase(request.getRol())) {
            user.setSold(0f); // clientul începe cu sold 0
        } else if ("manager".equalsIgnoreCase(request.getRol())) {
            user.setIdHotels(null); // managerul nu are hoteluri la început
        } else {
            throw new RuntimeException("Rol invalid (trebuie să fie 'client' sau 'manager')");
        }

        userRepository.saveUser(user);

        return new RegisterResponseDto("Utilizator înregistrat cu succes!");
    }

}
