

package com.EcoMove.Controladores;

import com.EcoMove.Entidades.Prestamo;
import com.EcoMove.ImplementService.PrestamoService;
import org.springframework.http.HttpStatus;


import com.EcoMove.InterfaceServices.IPrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final IPrestamoService prestamoService;

    public PrestamoController(IPrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Prestamo> crear(@RequestBody Prestamo p) {
        return ResponseEntity.ok(prestamoService.crearPrestamo(p));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Prestamo> finalizar(
            @PathVariable String id,
            @RequestParam int minutos,
            @RequestParam String metodo
    ) {
        return ResponseEntity.ok(prestamoService.finalizarPrestamo(id, minutos, metodo));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Prestamo>> historial(@PathVariable String usuarioId) {
        return ResponseEntity.ok(prestamoService.historialPorUsuario(usuarioId));
    }
}
