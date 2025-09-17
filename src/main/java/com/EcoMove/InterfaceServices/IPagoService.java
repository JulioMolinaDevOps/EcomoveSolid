package com.EcoMove.InterfaceServices;

import com.EcoMove.Entidades.Pago;
import java.util.List;
import java.util.Optional;

public interface IPagoService {
	Pago registrar(Pago pago);
	List<Pago> listar();
	Optional<Pago> buscarPorId(String id);
}
