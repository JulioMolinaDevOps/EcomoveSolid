package com.EcoMove.ImplementService;


import com.EcoMove.Entidades.Pago;
import com.EcoMove.InterfaceServices.IPagoService;
import com.EcoMove.Repositorios.PagoRepositorio;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService implements IPagoService {
    private final PagoRepositorio pagoRepositorio;

    public PagoService(PagoRepositorio pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
    }

    public Pago registrar(Pago pago) {
        return pagoRepositorio.save(pago);
    }


    public List<Pago> listar() {
        return pagoRepositorio.findAll();
    }


    public Optional<Pago> buscarPorId(String id) {
        return pagoRepositorio.findById(id);
    }
}

