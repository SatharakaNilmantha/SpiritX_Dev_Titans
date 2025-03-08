package com.example.SpiritX_Dev_Titans_01_Server_Side.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;      // JWT Token
    private String message;    // Additional message (for success or error)
    private int statusCode;    // HTTP status code (for easier error handling)
}
