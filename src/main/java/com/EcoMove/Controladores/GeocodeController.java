package com.EcoMove.Controladores;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/geocode")
public class GeocodeController {

    private final RestTemplate restTemplate = new RestTemplate();

    // Leemos la variable de entorno para el correo
    @Value("${GEOCODE_USER_AGENT_EMAIL}")
    private String userAgentEmail;

    @GetMapping
    public ResponseEntity<Object> geocode(@RequestParam String address) {
        try {
            // Codificamos la dirección
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);

            // URL de Nominatim
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress;

            // Configuramos headers obligatorios
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "EcoMoveApp/1.0 (" + userAgentEmail + ")");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Llamada a Nominatim
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            // Log del error para producción
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error en geocodificación: " + e.getMessage());
        }
    }
}
