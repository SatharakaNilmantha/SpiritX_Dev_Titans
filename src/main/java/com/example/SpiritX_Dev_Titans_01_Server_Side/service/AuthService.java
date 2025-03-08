package com.example.SpiritX_Dev_Titans_01_Server_Side.service;

import com.example.SpiritX_Dev_Titans_01_Server_Side.dto.AuthRequest;
import com.example.SpiritX_Dev_Titans_01_Server_Side.dto.AuthResponse;

public interface AuthService {
    AuthResponse signUp(AuthRequest request);
    AuthResponse signIn(AuthRequest request);
}
