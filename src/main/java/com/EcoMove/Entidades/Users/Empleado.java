package com.EcoMove.Entidades.Users;

import org.springframework.data.mongodb.core.mapping.Document;

import com.EcoMove.Entidades.Usuario;

@Document(collection = "usuarios")
public class Empleado extends Usuario {
    private String area_Trabajo;

}