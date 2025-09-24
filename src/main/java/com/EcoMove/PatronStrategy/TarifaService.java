package com.EcoMove.PatronStrategy;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifaService {
    private final List<TarifaStrategy> estrategias;

    public TarifaService(List<TarifaStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    public double calcularTarifa(String tipoTransporte, int minutos) {
        return estrategias.stream()
                .filter(e -> e.tipoTransporte().equalsIgnoreCase(tipoTransporte))
                .findFirst()
                .map(e -> e.calcular(minutos))
                .orElseThrow(() -> new IllegalArgumentException("Transporte no "));
    }
}
