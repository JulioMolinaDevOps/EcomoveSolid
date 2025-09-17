package com.EcoMove.dto;

import com.EcoMove.Entidades.enums.TipoUsuario;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String userId;
    private String nombre;
    private String correo;
    private TipoUsuario tipo;
}