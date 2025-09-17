
package com.EcoMove.Controladores;

import com.EcoMove.Entidades.Usuario;
import com.EcoMove.Repositorios.UsuarioRepositorio;
import com.EcoMove.InterfaceServices.IJwtService;
import com.EcoMove.InterfaceServices.ISecurityService;
import com.EcoMove.dto.LoginRequestDTO;
import com.EcoMove.dto.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {
    private final UsuarioRepositorio usuarioRepositorio;
    private final ISecurityService securityService;
    private final IJwtService jwtService;

    public UsuarioControlador(UsuarioRepositorio usuarioRepositorio, ISecurityService securityService, IJwtService jwtService) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.securityService = securityService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario u) {
        // Hash de la contraseña antes de guardar
        u.setPassword(securityService.hashPassword(u.getPassword()));
        Usuario usuarioGuardado = usuarioRepositorio.save(u);
        return ResponseEntity.ok(usuarioGuardado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioRepositorio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable String id) {
        return usuarioRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        Usuario usuario = usuarioRepositorio.findAll().stream()
                .filter(u -> u.getCorreo().equals(loginRequest.getCorreo()))
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        if (!securityService.verifyPassword(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.badRequest().body("Contraseña incorrecta");
        }

        // Generar token JWT
        String token = jwtService.generateToken(usuario.getId(), usuario.getCorreo());
        
        // Crear respuesta
        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setUserId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setCorreo(usuario.getCorreo());
        response.setTipo(usuario.getTipo());
        
        return ResponseEntity.ok(response);
    }
}

package com.EcoMove.Controladores;

import com.EcoMove.Entidades.Usuario;
import com.EcoMove.InterfaceServices.IUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {
    private final IUsuarioService usuarioService;

    public UsuarioControlador(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario u) {
        return ResponseEntity.ok(usuarioService.crear(u));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable String id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

