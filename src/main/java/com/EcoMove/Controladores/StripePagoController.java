package com.EcoMove.Controladores;

import com.EcoMove.dto.StripePagoRequestDTO;
import com.EcoMove.servicio.StripePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pago-online/stripe")
public class StripePagoController {
    @Autowired
    private StripePagoService stripePagoService;

    @PostMapping("/checkout")
    public ResponseEntity<?> crearCheckout(@RequestBody StripePagoRequestDTO request) {
        try {
            String url = stripePagoService.crearSesionPago(request.getMonto(), request.getDescripcion());
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
