package com.EcoMove.Entidades;

import com.EcoMove.Entidades.enums.TipoUsuario;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String correo;
    private String documento;
    private String password;
    private TipoUsuario tipo = TipoUsuario.USUARIO; // Por defecto es usuario normal
}
