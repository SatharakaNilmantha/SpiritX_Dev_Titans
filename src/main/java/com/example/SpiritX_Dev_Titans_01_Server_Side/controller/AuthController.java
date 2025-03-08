package com.example.SpiritX_Dev_Titans_01_Server_Side.controller;


import com.example.SpiritX_Dev_Titans_01_Server_Side.dto.AuthRequest;
import com.example.SpiritX_Dev_Titans_01_Server_Side.dto.AuthResponse;
import com.example.SpiritX_Dev_Titans_01_Server_Side.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest request) {
        try {
            // Call the service method and return response with status 201 (Created)
            AuthResponse authResponse = authService.signUp(request);
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);  // Status 201: Created
        } catch (RuntimeException e) {
            // Handle exception and return error response
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage(e.getMessage());
            authResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());  // Status 400: Bad Request
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest request) {
        try {
            AuthResponse authResponse = authService.signIn(request);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);  // Return the token with a success message
        } catch (RuntimeException e) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage(e.getMessage());
            authResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);  // Return error message
        }
    }
}
