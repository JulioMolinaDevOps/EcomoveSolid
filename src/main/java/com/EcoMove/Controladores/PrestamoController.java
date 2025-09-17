package com.EcoMove.Controladores;


import com.EcoMove.Entidades.Prestamo;
import com.EcoMove.ImplementService.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Prestamo> crear(@RequestBody Prestamo p) {
        Prestamo nuevo = service.crearPrestamo(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Prestamo> finalizar(@PathVariable String id,
                                              @RequestParam int minutos,
                                              @RequestParam(defaultValue = "EFECTIVO") String metodo) {
        try {
            Prestamo actualizado = service.finalizarPrestamo(id, minutos, metodo);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Prestamo>> historial(@PathVariable String usuarioId) {
        List<Prestamo> prestamos = service.historialPorUsuario(usuarioId);
        if (prestamos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prestamos);
    }
}
