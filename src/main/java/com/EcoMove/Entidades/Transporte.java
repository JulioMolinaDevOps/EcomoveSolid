package com.EcoMove.Entidades;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "transportes")
public class Transporte {
    @Id
    private String id;
    private String tipo;
    private String estado;
    private String marca;
    private Double velocidadMaxima;
}
