package com.EcoMove.PatronStrategy;

public interface TarifaStrategy {
    double calcular(int minutos);
    String tipoTransporte();
}
