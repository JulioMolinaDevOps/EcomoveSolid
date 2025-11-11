package com.EcoMove.PatronStrategy;

public interface TarifaStrategy {
    String tipoTransporte();
    double calcular(int minutos);
}
