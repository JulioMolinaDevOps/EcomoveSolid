package com.EcoMove.InterfaceServices;

import io.jsonwebtoken.Claims;

public interface IJwtService {
    String generateToken(String userId, String email);
    Claims validateToken(String token);
}