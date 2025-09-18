package com.EcoMove.dto;

public class StripePagoRequestDTO {
    private String usuarioId;
    private String transporteId;
    private Double monto;

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getTransporteId() { return transporteId; }
    public void setTransporteId(String transporteId) { this.transporteId = transporteId; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
}
