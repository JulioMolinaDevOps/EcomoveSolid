package com.EcoMove.InterfaceServices;

import com.EcoMove.Entidades.Transporte;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ITransporteService {
    Transporte crear(Transporte e);
    List<Transporte> listar();
    Optional<Transporte> buscarPorId(String id);
}
