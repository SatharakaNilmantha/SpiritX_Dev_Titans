package com.example.SpiritX_Dev_Titans_01_Server_Side.service;


import com.example.SpiritX_Dev_Titans_01_Server_Side.config.JwtUtil;
import com.example.SpiritX_Dev_Titans_01_Server_Side.dto.AuthRequest;

import com.example.SpiritX_Dev_Titans_01_Server_Side.dto.AuthResponse;
import com.example.SpiritX_Dev_Titans_01_Server_Side.entity.User;
import com.example.SpiritX_Dev_Titans_01_Server_Side.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(AuthRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User Already Exists!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());

        // Return response with token, success message, and status code
        return new AuthResponse(token, "User Registered Successfully", HttpStatus.CREATED.value());
    }

    @Override
    public AuthResponse signIn(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, "Login successful", HttpStatus.OK.value());  // Return token with success message
    }
}
