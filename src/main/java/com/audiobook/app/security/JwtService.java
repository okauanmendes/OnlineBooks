package com.audiobook.app.security;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String createToken(String username) {
        return "token";
    }

    public boolean validateToken(String token) {
        return true;
    }
}
