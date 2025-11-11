package com.EcoMove.Entidades;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transportes")
@Data
public class Transporte {
    // getters y setters
    @Getter
    @Id
    private String id;
    private String tipo;
    private String estado;
    private String marca;



}
