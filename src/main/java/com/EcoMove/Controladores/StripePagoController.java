package com.EcoMove.Controladores;

import com.EcoMove.dto.StripePagoRequestDTO;
import com.EcoMove.servicio.StripePagoService;
import com.EcoMove.InterfaceServices.IUsuarioService;
import com.EcoMove.InterfaceServices.ITransporteService;
import com.EcoMove.Entidades.Usuario;
import com.EcoMove.Entidades.Transporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pago-online/stripe")
public class StripePagoController {
    @Autowired
    private StripePagoService stripePagoService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ITransporteService transporteService;

    @PostMapping("/checkout")
    public ResponseEntity<?> crearCheckout(@RequestBody StripePagoRequestDTO request,
                                           @RequestParam(required = false) String currency) {
        try {
            if (request.getMonto() == null || request.getUsuarioId() == null || request.getTransporteId() == null) {
                return ResponseEntity.badRequest().body("Faltan campos obligatorios: usuarioId, transporteId o monto");
            }
            String usuarioNombre = request.getUsuarioId();
            try {
                java.util.Optional<Usuario> uOpt = usuarioService.buscarPorId(request.getUsuarioId());
                if (uOpt.isPresent() && uOpt.get().getNombre() != null && !uOpt.get().getNombre().isBlank()) {
                    usuarioNombre = uOpt.get().getNombre();
                }
            } catch (Exception ignored) {}

            String tipoTransporte = request.getTransporteId();
            try {
                java.util.Optional<Transporte> tOpt = transporteService.buscarPorId(request.getTransporteId());
                if (tOpt.isPresent() && tOpt.get().getTipo() != null && !tOpt.get().getTipo().isBlank()) {
                    tipoTransporte = tOpt.get().getTipo();
                }
            } catch (Exception ignored) {}

            String descripcion = "Alquiler " + tipoTransporte + " por " + usuarioNombre;
            String url = stripePagoService.crearSesionPago(request.getMonto(), descripcion, currency);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
