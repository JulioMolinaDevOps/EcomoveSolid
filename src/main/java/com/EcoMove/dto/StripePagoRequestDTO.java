package com.EcoMove.dto;

public class StripePagoRequestDTO {
    private Double monto;
    private String descripcion;

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
