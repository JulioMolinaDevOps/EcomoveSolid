package com.EcoMove.InterfaceServices;

import com.EcoMove.Entidades.Usuario;


import java.util.List;
import java.util.Optional;


public interface IUsuarioService {
    Usuario crear(Usuario u);
    List<Usuario> listar();
    Optional<Usuario>buscarPorId(String id);
}
