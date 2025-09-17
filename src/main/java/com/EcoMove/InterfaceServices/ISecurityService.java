package com.EcoMove.InterfaceServices;

public interface ISecurityService {
    String hashPassword(String plainPassword);
    boolean verifyPassword(String plainPassword, String hashedPassword);
}