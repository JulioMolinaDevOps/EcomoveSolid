package com.EcoMove.InterfaceServices;

import com.EcoMove.Entidades.Transporte;
import com.sun.jdi.connect.Transport;

import java.util.List;
import java.util.Optional;

public interface ITransporteService {
    Transporte crear(Transporte e);
    List<Transporte> listar();
    Optional<Transporte> buscarPorId(String id);
}
