package com.EcoMove.ImplementService;

import com.EcoMove.Entidades.Pago;
import com.EcoMove.Entidades.Prestamo;
import com.EcoMove.InterfaceServices.IPagoService;
import com.EcoMove.InterfaceServices.IPrestamoService;
import com.EcoMove.PatronStrategy.TarifaService;
import com.EcoMove.Repositorios.PrestamoRepositorio;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;



@Service
public class PrestamoService implements IPrestamoService {
    private final PrestamoRepositorio prestamoRepo;
    private final IPagoService pagoService;
    private final TarifaService tarifaService;

    public PrestamoService(PrestamoRepositorio prestamoRepo,
                           IPagoService pagoService,
                           TarifaService tarifaService) {
        this.prestamoRepo = prestamoRepo;
        this.pagoService = pagoService;
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

        // Crear y registrar el pago a través de PagoService
        Pago pago = new Pago();
        pago.setMonto(costo);
        pago.setFecha(LocalDateTime.now());
        pago.setMetodo(metodo);
        pago = pagoService.registrar(pago); // <<<< Se usa el servicio, no el repositorio

        p.setPagoId(pago.getId());
        return prestamoRepo.save(p);
    }

    @Override
    public List<Prestamo> historialPorUsuario(String usuarioId) {
        return prestamoRepo.findAll().stream()
                .filter(p -> p.getUsuarioId().equals(usuarioId))
                .toList();
    }
}
