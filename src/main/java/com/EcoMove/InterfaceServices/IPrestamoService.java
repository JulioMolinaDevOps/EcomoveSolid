package com.EcoMove.InterfaceServices;

import com.EcoMove.Entidades.Prestamo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IPrestamoService {
    Prestamo crearPrestamo(Prestamo p);
    Prestamo finalizarPrestamo(String id, int minutos, String metodo);
    List<Prestamo> historialPorUsuario(String usuarioId);
}
