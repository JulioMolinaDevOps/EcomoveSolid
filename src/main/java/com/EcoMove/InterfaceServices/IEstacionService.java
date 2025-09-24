package com.EcoMove.InterfaceServices;

import com.EcoMove.Entidades.Estacion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface IEstacionService {
    Estacion crear(Estacion e);
    List<Estacion> listar();
    Optional<Estacion> buscarPorId(String id);
}
