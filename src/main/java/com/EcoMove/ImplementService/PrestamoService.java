package com.EcoMove.ImplementService;

import com.EcoMove.Entidades.Prestamo;
import com.EcoMove.Entidades.Pago;
import com.EcoMove.Entidades.Reembolso;
import com.EcoMove.InterfaceServices.IPagoService;
import com.EcoMove.InterfaceServices.IPrestamoService;
import com.EcoMove.InterfaceServices.IReembolsoService;
import com.EcoMove.PatronStrategy.TarifaService;
import com.EcoMove.Repositorios.PrestamoRepositorio;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrestamoService implements IPrestamoService {
    private final PrestamoRepositorio prestamoRepo;
    private final IPagoService pagoService;
    private final IReembolsoService reembolsoService;
    private final TarifaService tarifaService;

    public PrestamoService(PrestamoRepositorio prestamoRepo,
                           IPagoService pagoService,
                           IReembolsoService reembolsoService,
                           TarifaService tarifaService) {
        this.prestamoRepo = prestamoRepo;
        this.pagoService = pagoService;
        this.reembolsoService = reembolsoService;
        this.tarifaService = tarifaService;
    }

    @Override
    public Prestamo crearPrestamo(Prestamo p) {
        p.setInicio(LocalDateTime.now());
        p.setCosto(0);
        return prestamoRepo.save(p);
    }

    @Override
    public Prestamo finalizarPrestamo(String id, int minutos, String metodo) {
        Prestamo p = prestamoRepo.findById(id).orElseThrow();
        p.setFin(LocalDateTime.now());

        double costo = tarifaService.calcularTarifa(p.getTransporteId(), minutos);
        p.setCosto(costo);

        // Simulación: asumimos que el usuario pagó un depósito fijo (ej: 50)
        double deposito = 50.0;

        // Registrar el pago inicial (depósito)
        Pago pago = new Pago();
        pago.setMonto(deposito);
        pago.setFecha(LocalDateTime.now());
        pago.setMetodo(metodo);
        pago = pagoService.registrar(pago);

        p.setPagoId(pago.getId());

        // Si el costo real es menor al depósito, devolver la diferencia
        if (costo < deposito) {
            double diferencia = deposito - costo;

            Reembolso r = new Reembolso();
            r.setUsuarioId(p.getUsuarioId());
            r.setPrestamoId(p.getId());
            r.setMonto(diferencia);
            r.setFecha(LocalDateTime.now());
            r.setMetodo(metodo);
            reembolsoService.registrar(r);
        }

        return prestamoRepo.save(p);
    }

    @Override
    public List<Prestamo> historialPorUsuario(String usuarioId) {
        return prestamoRepo.findAll().stream()
                .filter(p -> p.getUsuarioId().equals(usuarioId))
                .toList();
    }
}
