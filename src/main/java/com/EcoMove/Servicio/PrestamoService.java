package com.EcoMove.Servicio;



import com.EcoMove.Entidades.*;
import com.EcoMove.Repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrestamoService {
    private final PrestamoRepositorio prestamoRepo;
    private final UsuarioRepositorio usuarioRepo;
    private final TransporteRepositorio transporteRepo;
    private final PagoRepositorio pagoRepo;

    public PrestamoService(PrestamoRepositorio pr, UsuarioRepositorio ur, TransporteRepositorio tr, PagoRepositorio pg) {
        this.prestamoRepo = pr;
        this.usuarioRepo = ur;
        this.transporteRepo = tr;
        this.pagoRepo = pg;
    }

    public Prestamo crearPrestamo(Prestamo p) {
        p.setInicio(LocalDateTime.now());
        p.setCosto(0);
        return prestamoRepo.save(p);
    }

    public Prestamo finalizarPrestamo(String id, int minutos, String metodo) {
        Prestamo p = prestamoRepo.findById(id).orElseThrow();
        p.setFin(LocalDateTime.now());

        double tarifa = 0.5;
        p.setCosto(minutos * tarifa);

        Pago pago = new Pago();
        pago.setMonto(p.getCosto());
        pago.setFecha(LocalDateTime.now());
        pago.setMetodo(metodo);
        pagoRepo.save(pago);

        p.setPagoId(pago.getId());
        return prestamoRepo.save(p);
    }

    public List<Prestamo> historialPorUsuario(String usuarioId) {
        return prestamoRepo.findAll().stream()
                .filter(p -> p.getUsuarioId().equals(usuarioId))
                .toList();
    }
}
